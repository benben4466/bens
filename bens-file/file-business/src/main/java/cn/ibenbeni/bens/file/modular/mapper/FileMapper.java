package cn.ibenbeni.bens.file.modular.mapper;

import cn.ibenbeni.bens.db.api.pojo.page.PageResult;
import cn.ibenbeni.bens.db.api.pojo.query.LambdaQueryWrapperX;
import cn.ibenbeni.bens.db.mp.mapper.BaseMapperX;
import cn.ibenbeni.bens.file.modular.entity.FileDO;
import cn.ibenbeni.bens.file.modular.pojo.request.FilePageReq;

/**
 * 文件信息-Mapper接口
 *
 * @author: benben
 * @time: 2025/6/20 下午5:01
 */
public interface FileMapper extends BaseMapperX<FileDO> {

    default PageResult<FileDO> selectPage(FilePageReq req) {
        return selectPage(req, new LambdaQueryWrapperX<FileDO>()
                .likeIfPresent(FileDO::getFilePath, req.getFilePath())
                .likeIfPresent(FileDO::getFileType, req.getFileType())
                .betweenIfPresent(FileDO::getCreateTime, req.getCreateTime())
                .orderByDesc(FileDO::getFileId)
        );
    }

}
