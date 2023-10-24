package capstone.nerfserver.service;

import capstone.nerfserver.domain.MeshInfo;
import capstone.nerfserver.domain.Post;
import capstone.nerfserver.repository.*;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;


import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Optional;


@Service
@RequiredArgsConstructor

public class PostService {


    private final Logger log = LoggerFactory.getLogger(getClass());
    private final MariaDBPostRepository postRepository;
    private final MariaDBMeshInfoRepository meshInfoRepository;
    private final String scriptPath = "/workspace/test.sh";//~~.sh
    private final String videoPath = "/workspace/video/";
    private final String imagePath = "/workspace/image/";
    private final String meshPath = "/workspace/result/mesh/";

    //글수정(구현x)


    /**
     * 글 등록
     * @param post
     * @return
     */
    public Long upload(Post post){
        postRepository.save(post);
        return post.getId();
    }

    /**
     * NeRF 실행
     * @param id
     */
    public void runNerf(Long id){
        try {
            Runtime.getRuntime().exec(scriptPath + " " + id);
            log.info("start running {} {}", scriptPath, id);
        } catch (IOException e) {
            log.info("runNerf error\n{}", e.getMessage());
            throw new RuntimeException(e);
        }
    }

    /**
     * NeRF 완료
     * @param id
     * @return
     */
    public Optional<Post> finishNerf(Long id){
        Optional<Post> post = postRepository.findById(id);
        post.ifPresent(p ->{p.setState("done");
            postRepository.update(p);});

        return post;
    }

    /**
     * 글 목록 조회
     * @return
     */
    public List<Post> allPosts(){
        return postRepository.findAll();
    }

    /**
     * 글 내용 조회
     * @param postId
     * @return
     */
    public Optional<Post> findPost(Long postId){
        return postRepository.findById(postId);
    }

    /**
     * MeshInfo 조회
     * @param postId
     * @return
     */
    public Optional<MeshInfo> findMeshInfo(Long postId){
        return meshInfoRepository.findById(postId);
    }

    /**
     * mesh파일 주소 조회(폴더 주소)
     * @param postId
     * @return
     */
    public String findMesh(Long postId){
        return meshPath + postId + "/";
    }

    public String findImage(Long postId){
        return imagePath + postId + "/";
    }
    public void saveMeshInfo(MeshInfo meshInfo){
        meshInfoRepository.save(meshInfo);
    }

    public void saveVideo(Long id, MultipartFile video) {
        if(!video.isEmpty()){
            try {
                File folder = new File(videoPath);
                if(!folder.exists()){
                    try{
                        folder.mkdir();
                    }
                    catch(Exception e){
                        e.getStackTrace();
                    }
                }

                video.transferTo(new File(videoPath + id + ".mp4"));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void saveImages(Long id, MultipartFile[] images, Long numberOfImages) {
        File imageFolder = new File(imagePath);
        if(!imageFolder.exists()){
            try{
                imageFolder.mkdir();
            }
            catch(Exception e){
                e.getStackTrace();
            }
        }


        File folder = new File(imagePath + id);
        if(!folder.exists()){
            try{
                folder.mkdir();
            }
            catch(Exception e){
                e.getStackTrace();
            }
        }

        for(int index=0; index<numberOfImages; index++){
            if(!images[index].isEmpty()){ //불필요?
                try {
                    images[index].transferTo(new File(folder.getPath() + "/" + index + ".png"));
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    public void clearStore(){
        postRepository.clearStore();
    }


}
