package capstone.nerfserver.repository;

import capstone.nerfserver.domain.MeshInfo;
import capstone.nerfserver.domain.Post;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

@SpringBootTest
public class MariaDBMeshInfoRepositoryTest {
/*
    @Autowired
    MariaDBMeshInfoRepository mariaDBMeshInfoRepository;

    @AfterEach
    void afterEach(){
        mariaDBMeshInfoRepository.clearStore();
    }

    @Test
    void save() {
        MeshInfo meshInfo = new MeshInfo(1L, 0.1, 0.2, 0.3);
        mariaDBMeshInfoRepository.save(meshInfo);
        MeshInfo result = mariaDBMeshInfoRepository.findById(1L).get();

        System.out.println("id: " + result.getId());
        System.out.println("xSize: " + result.getXSize());
        System.out.println("ySize: " + result.getYSize());
        System.out.println("zSize: " + result.getZSize());

        Assertions.assertThat(meshInfo.getXSize()).isEqualTo(result.getXSize());
    }

    @Test
    void update(){
        MeshInfo meshInfo = new MeshInfo(2L, 0.1, 0.2, 0.3);
        mariaDBMeshInfoRepository.save(meshInfo);
        meshInfo.setXSize(2.1);
        mariaDBMeshInfoRepository.update(meshInfo);
        MeshInfo result = mariaDBMeshInfoRepository.findById(2L).get();

        System.out.println("xSize: " + result.getXSize());

        Assertions.assertThat(meshInfo.getXSize()).isEqualTo(result.getXSize());
    }

    @Test
    void findById(){
        //given
        MeshInfo meshInfo = new MeshInfo(1L, 0.1, 0.2, 0.3);
        mariaDBMeshInfoRepository.save(meshInfo);

        //when
        MeshInfo result = mariaDBMeshInfoRepository.findById(1L).get();

        //then
        System.out.println("meshInfo id: " + meshInfo.getId());
        System.out.println("meshInfo xSize: " + meshInfo.getXSize());
        System.out.println("result id: " + result.getId());
        System.out.println("result xSize: " + result.getXSize());
        Assertions.assertThat(meshInfo.getId()).isEqualTo(result.getId());
    }
 */
}