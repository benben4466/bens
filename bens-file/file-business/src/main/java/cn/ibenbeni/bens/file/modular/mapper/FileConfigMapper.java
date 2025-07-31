package cn.ibenbeni.bens.file.modular.mapper;

import cn.ibenbeni.bens.db.api.pojo.page.PageResult;
import cn.ibenbeni.bens.db.api.pojo.query.LambdaQueryWrapperX;
import cn.ibenbeni.bens.db.mp.mapper.BaseMapperX;
import cn.ibenbeni.bens.file.modular.entity.FileConfigDO;
import cn.ibenbeni.bens.file.modular.pojo.request.FileConfigPageReq;

/**
 * 文件配置-Mapper
 */
public interface FileConfigMapper extends BaseMapperX<FileConfigDO> {

    default FileConfigDO selectByCode(String fileConfigCode) {
        return selectOne(FileConfigDO::getFileConfigCode, fileConfigCode);
    }

    default FileConfigDO selectByMasterFlag() {
        return selectOne(FileConfigDO::getMasterFlag, true);
    }

    default PageResult<FileConfigDO> selectPage(FileConfigPageReq req) {
        return selectPage(req, new LambdaQueryWrapperX<FileConfigDO>()
                .likeIfPresent(FileConfigDO::getFileConfigName, req.getFileConfigName())
                .likeIfPresent(FileConfigDO::getFileConfigCode, req.getFileConfigCode())
                .eqIfPresent(FileConfigDO::getFileStorage, req.getFileStorage())
                .betweenIfPresent(FileConfigDO::getCreateTime, req.getCreateTime())
                .orderByAsc(FileConfigDO::getFileConfigId)
        );
    }

}
