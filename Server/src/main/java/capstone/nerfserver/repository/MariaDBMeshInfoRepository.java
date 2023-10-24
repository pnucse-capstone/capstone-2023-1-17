package capstone.nerfserver.repository;

import capstone.nerfserver.domain.MeshInfo;
import org.apache.ibatis.annotations.Mapper;

import java.util.Optional;

@Mapper
public interface MariaDBMeshInfoRepository extends MeshInfoRepository{

    void save(MeshInfo meshInfo);
    void update(MeshInfo meshInfo);
    Optional<MeshInfo> findById(Long id);
    void clearStore();
}