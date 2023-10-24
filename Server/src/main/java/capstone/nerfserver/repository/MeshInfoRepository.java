package capstone.nerfserver.repository;

import capstone.nerfserver.domain.MeshInfo;

import java.util.Optional;

public interface MeshInfoRepository {

    void save(MeshInfo meshInfo);
    void update(MeshInfo meshInfo);  //If id doesn't exist, return false. Or else return true
    Optional<MeshInfo> findById(Long id);
}
