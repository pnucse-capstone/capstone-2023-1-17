package capstone.nerfserver.controller;

import capstone.nerfserver.domain.MeshInfo;
import capstone.nerfserver.domain.Post;
import capstone.nerfserver.service.PostService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

@Controller
public class NerfServerController {
    @Autowired
    PostService service;


    private final Logger log = LoggerFactory.getLogger(getClass());

    @PostMapping("upload")
    @ResponseBody
    public Post uploadPost(@RequestParam Long userId, @RequestParam String title, @RequestParam String content, @RequestParam Long price, @RequestParam Double xSize, @RequestParam Double ySize, @RequestParam Double zSize, @RequestParam MultipartFile video,
                           @RequestParam Long numberOfImages, @RequestParam MultipartFile image_1, @RequestParam MultipartFile image_2, @RequestParam MultipartFile image_3, @RequestParam MultipartFile image_4, @RequestParam MultipartFile image_5,
                           HttpServletRequest request) {
        logReceivedRequest(request);
        Post post = new Post(userId, title, content, price, numberOfImages);
        service.upload(post);
        service.saveMeshInfo(new MeshInfo(post.getId(), xSize, ySize, zSize));
        service.saveVideo(post.getId(), video);
        MultipartFile[] images = {image_1, image_2, image_3, image_4, image_5};
        service.saveImages(post.getId(), images, numberOfImages);
        service.runNerf(post.getId());

        return post;
    }

    @GetMapping("finishNerf")
    @ResponseBody
    public void finishNerf(@RequestParam("id") Long id, HttpServletRequest request) {
        if(!request.getRemoteAddr().equals("127.0.0.1")){  //only allow request from localhost(request from server itself)
            log.warn("finishNerf request from '{}'" ,request.getRemoteAddr());
            return;
        }
        Optional<Post> post = service.finishNerf(id);
        post.ifPresentOrElse(p -> log.info("id: {} NeRF {}", id, p.getState()), () -> log.warn("[finishNerfError] Wrong id (id: {})", id));
    }

    @GetMapping("postList")
    @ResponseBody
    public List<Post> getPostList(HttpServletRequest request) {
        logReceivedRequest(request);
        return service.allPosts();
    }

    @GetMapping("post")
    @ResponseBody
    public Post getPost(@RequestParam("id") Long id, HttpServletRequest request) {
        logReceivedRequest(request);
        return service.findPost(id).orElseGet(() -> {
            Post p = new Post();
            p.setTitle("Wrong ID");
            return p;
        }); //존재하지 않는 글이면 title이 "Wrong ID"인 Post객체 전송
    }

    @GetMapping("meshInfo")
    @ResponseBody
    public MeshInfo getMeshInfo(@RequestParam("id") Long id, HttpServletRequest request) {
        logReceivedRequest(request);
        return service.findMeshInfo(id).orElseGet(() -> {
            return new MeshInfo(-1L, -1.0, -1.0, -1.0);
        }); //존재하지 않는 글이면 -1,-1,-1인 MeshInfo전송
    }

    @GetMapping("obj")
    @ResponseBody
    public void getObj(@RequestParam("id") Long id, HttpServletResponse response, HttpServletRequest request) {
        logReceivedRequest(request);
        if(service.findPost(id).isEmpty()){
            log.warn("[objError] Wrong id (id: {})", id);
            return;
        }
        if(service.findPost(id).get().getState().equals("waiting")){  //waiting상태인 글의 mesh를 요청하면 body에 아무것도 없이 전송(즉 Content-Length가 0)
            log.warn("[objError] Status is \"waiting\" (id: {})", id);
            return;
        }
        String path = service.findMesh(id);

        File obj = new File(path + "mesh.obj");

        sendFile(response, obj);
    }

    @GetMapping("mtl")
    @ResponseBody
    public void getMtl(@RequestParam("id") Long id, HttpServletResponse response, HttpServletRequest request) {
        logReceivedRequest(request);
        if(service.findPost(id).isEmpty()){
            log.warn("[mtlError] Wrong id (id: {})", id);
            return;
        }
        if(service.findPost(id).get().getState().equals("waiting")){  //waiting상태인 글의 mesh를 요청하면 body에 아무것도 없이 전송(즉 Content-Length가 0)
            log.warn("[mtlError] Status is \"waiting\" (id: {})", id);
            return;
        }
        String path = service.findMesh(id);

        File mtl = new File(path + "material_0.mtl");

        sendFile(response, mtl);
    }

    @GetMapping("png")
    @ResponseBody
    public void getPng(@RequestParam("id") Long id, HttpServletResponse response, HttpServletRequest request) {
        logReceivedRequest(request);
        if(service.findPost(id).isEmpty()){
            log.warn("[pngError] Wrong id (id: {})", id);
            return;
        }
        if(service.findPost(id).get().getState().equals("waiting")){  //waiting상태인 글의 mesh를 요청하면 body에 아무것도 없이 전송(즉 Content-Length가 0)
            log.warn("[pngError] Status is \"waiting\" (id: {})", id);
            return;
        }
        String path = service.findMesh(id);

        File png = new File(path + "material_0.png");

        sendFile(response, png);
    }

    private void sendFile(HttpServletResponse response, File file) {
        log.info("Start sending file '{}'", file.getName());
        response.setHeader("Content-Disposition", "attachment;fileName=" + file.getName());

        try {
            FileInputStream fileInputStream = new FileInputStream(file);
            OutputStream outputStream = response.getOutputStream();

            int read = 0;
            byte[] buffer = new byte[1024];
            while ((read = fileInputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, read);
                outputStream.flush();
            }
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        log.info("Sent file '{}'", file.getName());
    }
    /*
    @GetMapping("mesh")
    @ResponseBody
    public void getMesh(@RequestParam("id") Long id, HttpServletResponse response) {
        if(service.findPost(id).isEmpty()){
            printWithTimestamp("[MeshError] Wrong id(id: " + id + ")");
            return;
        }
        if(service.findPost(id).get().getState() == "waiting"){  //waiting상태인 글의 mesh를 요청하면 body에 아무것도 없이 전송(즉 Content-Length가 0)
            printWithTimestamp("[MeshError] Status is \"waiting\"(id: " + id + ")");
            return;
        }
        String path = service.findMesh(id);

        File obj = new File(path + "mesh.obj");
        File mtl = new File(path + "material_0.mtl");
        File png = new File(path + "material_0.png");

        response.setStatus(HttpServletResponse.SC_OK);
        response.setContentType("application/zip");
        response.addHeader("Content-Disposition", "attachment; filename=\"mesh.zip\"");

        FileOutputStream fos = null;

        try {
            ZipOutputStream zipOutputStream = new ZipOutputStream(response.getOutputStream());

            // File 객체를 List에 담는다.
            List<File> fileList = Arrays.asList(obj, mtl, png);

            // 루프를 돌며 ZipOutputStream에 파일들을 계속 주입해준다.
            for(File file : fileList) {
                zipOutputStream.putNextEntry(new ZipEntry(file.getName()));
                FileInputStream fileInputStream = new FileInputStream(file);

                StreamUtils.copy(fileInputStream, zipOutputStream);

                fileInputStream.close();
                zipOutputStream.closeEntry();
            }
            zipOutputStream.close();

        } catch (Exception e) {
            printWithTimestamp(e.getMessage());
        }
    }
    */


    @GetMapping("image")
    @ResponseBody
    public void getImage(@RequestParam("id") Long id, HttpServletResponse response, HttpServletRequest request) {
        logReceivedRequest(request);
        if(service.findPost(id).isEmpty()){
            log.warn("[imageError] Wrong id (id: {})", id);
            return;
        }
        String path = service.findImage(id);
        Long numberOfImages = service.findPost(id).get().getNumberOfImages();

        response.setStatus(HttpServletResponse.SC_OK);
        response.setContentType("application/zip");
        response.addHeader("Content-Disposition", "attachment; filename=\"image.zip\"");

        try {
            ZipOutputStream zipOutputStream = new ZipOutputStream(response.getOutputStream());

            // File 객체를 List에 담는다.
            List<File> fileList = new ArrayList<>();
            for(int index=0; index<numberOfImages; index++){
                fileList.add(new File(path + index + ".png"));
            }

            // 루프를 돌며 ZipOutputStream에 파일들을 계속 주입해준다.
            for(File file : fileList) {
                zipOutputStream.putNextEntry(new ZipEntry(file.getName()));
                FileInputStream fileInputStream = new FileInputStream(file);

                StreamUtils.copy(fileInputStream, zipOutputStream);

                fileInputStream.close();
                zipOutputStream.closeEntry();
            }
            zipOutputStream.close();

        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }

    private void logReceivedRequest(HttpServletRequest request){
        String requestString = request.getRequestURL().toString();
        if(request.getQueryString() != null){
             requestString += "?" + request.getQueryString();
        }
        log.info("[Received Request] {}", requestString);
    }
}

