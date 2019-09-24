package com.changjinglu.domain.plugins.entity;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * <p> FileType </p>
 * <p> 生成代码文件类型枚举 </p>
 * @since 2019/9/24 4:36 下午
 * @author fengxiao
 */
public enum FileType {
    /** Rest服务层 */
    CONTROLLER("DomainController.java.vm", "Rest服务层", "controller"),
    /** 仓储层 - 实体 */
    REPO_DO("repository/DomainDO.java.vm", "仓储层 - 实体", "controller"),
    /** 仓储层 - 查询实体 */
    REPO_QUERY("repository/DomainQuery.java.vm", "仓储层 - 查询实体", "controller"),
    /** 仓储层 - 数据库映射接口 */
    REPO_MAPPER("repository/DomainMapper.java.vm", "仓储层 - 数据库映射接口", "controller"),
    /** 仓储层 - 数据操作管理接口 */
    REPO_MANAGER("repository/DomainManager.java.vm", "仓储层 - 数据操作管理接口", "controller"),
    /** 仓储层 - 数据操作管理实现 */
    REPO_MANAGER_IMPL("repository/DomainManagerImpl.java.vm", "仓储层 - 数据操作管理实现", "controller"),
    /** 仓储层 - 仓储实现 */
    REPOSITORY_IMPL("repository/DomainRepositoryImpl.java.vm", "仓储层 - 仓储实现", "controller"),
    /** 实体层 - 仓储接口 */
    DOMAIN_REPOSITORY("domain/DomainRepository.java.vm","实体层 - 仓储接口", "controller"),
    /** 实体层 - 仓储结果实体 */
    DOMAIN_VO("domain/DomainVO.java.vm","实体层 - 仓储结果实体", "controller"),
    /** 实体层 - 仓储查询实体 */
    DOMAIN_REPO_QUERY("domain/DomainRepository.java.vm","实体层 - 仓储查询实体", "controller"),
    /** 实体层 - 实体 */
    DOMAIN_ENTITY("domain/DomainEntity.java.vm","实体层 - 实体", "controller"),
    /** 实体层 - 实体工厂 */
    DOMAIN_FACTORY("domain/DomainFactory.java.vm","实体层 - 实体工厂", "controller"),
    /** 实体层 - 实体构建模型 */
    DOMAIN_BUILD_VO("domain/DomainBuildVO.java.vm","实体层 - 实体构建模型", "controller"),
    /** 开放接口层 - 创建接口参数 */
    API_CREATE_PARAM("api/DomainCreateParam.java.vm","开放接口层 - 创建接口参数", "controller"),
    /** 开放接口层 - 修改接口参数 */
    API_UPDATE_PARAM("api/DomainUpdateParam.java.vm","开放接口层 - 修改接口参数", "controller"),
    /** 开放接口层 - 查询接口参数 */
    API_QUERY_PARAM("api/DomainQueryParam.java.vm","开放接口层 - 查询接口参数", "controller"),
    /** 开放接口层 - 分页查询接口参数 */
    API_PAGE_QUERY_PARAM("api/DomainPageQueryParam.java.vm","开放接口层 - 分页查询接口参数", "controller"),
    /** 开放接口层 - ID接口参数 */
    API_ID_PARAM("api/DomainIDParam.java.vm","开放接口层 - ID接口参数", "controller"),
    /** 开放接口层 - 结果模型 */
    API_DTO("api/DomainDTO.java.vm","开放接口层 - 结果模型", "controller"),
    /** 开放接口层 - 接口定义 */
    API_INTERFACE("api/DomainControllerApi.java.vm","开放接口层 - 接口定义", "api")
    ;
    /**
     * 模版文件
     */
    private String templateFile;
    /**
     * 文件描述
     */
    private String pkg;
    /**
     * 父包名
     */
    private String desc;

    FileType(String templateFile, String desc, String pkg) {
        this.templateFile = templateFile;
        this.desc = desc;
        this.pkg = pkg;
    }

    /**
     * 获取模板文件路径
     * @return
     */
    public String getTemplateFile() {
        return templateFile;
    }

    /**
     * 获取类型描述
     * @return
     */
    public String getDesc() {
        return desc;
    }

    private static Map<String, FileType> cache = new HashMap<>();
    static {
        Arrays.stream(FileType.values()).forEach(t -> cache.put(t.name(), t));
    }

    /**
     * 根据名称获取类型枚举
     * @param name
     * @return
     */
    public static FileType find(String name){
        return cache.get(name);
    }
}