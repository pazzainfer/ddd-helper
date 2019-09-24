package com.changjinglu.domain.plugins.entity;

import com.intellij.openapi.project.Project;
import java.io.Serializable;
import java.util.Set;
/**
 * <p> GeneratorConfig </p>
 * <p> 代码生成配置 </p>
 * @since 2019/9/24 2:26 下午
 * @author fengxiao
 */
public class GeneratorConfig implements Serializable {
  private static final long serialVersionUID = -8978327106950673087L;
  private String removeTablePrefix;
  private String removeFieldPrefix;
  private String extendBaseClass;
  private String entityPackage;
  private String repositoryPackage;
  private Set<String> excludeFields;
  /**
   * equal to {@link Project#getBasePath()}
   */
  private String projectBasePath;
  private String entityDirectory;
  private String repositoryDirectory;
  private boolean useLombok;
  private boolean generateRepository;
  private boolean implementSerializable;
  private boolean generateClassComment;
  private boolean generateFieldComment;
  private boolean generateMethodComment;
  private boolean useJava8DataType;

  public String getRemoveTablePrefix() {
    return removeTablePrefix;
  }

  public void setRemoveTablePrefix(String removeTablePrefix) {
    this.removeTablePrefix = removeTablePrefix;
  }

  public String getRemoveFieldPrefix() {
    return removeFieldPrefix;
  }

  public void setRemoveFieldPrefix(String removeFieldPrefix) {
    this.removeFieldPrefix = removeFieldPrefix;
  }

  public String getExtendBaseClass() {
    return extendBaseClass;
  }

  public void setExtendBaseClass(String extendBaseClass) {
    this.extendBaseClass = extendBaseClass;
  }

  public String getEntityPackage() {
    return entityPackage;
  }

  public void setEntityPackage(String entityPackage) {
    this.entityPackage = entityPackage;
  }

  public String getRepositoryPackage() {
    return repositoryPackage;
  }

  public void setRepositoryPackage(String repositoryPackage) {
    this.repositoryPackage = repositoryPackage;
  }

  public boolean isUseLombok() {
    return useLombok;
  }

  public void setUseLombok(boolean useLombok) {
    this.useLombok = useLombok;
  }

  public boolean isGenerateRepository() {
    return generateRepository;
  }

  public void setGenerateRepository(boolean generateRepository) {
    this.generateRepository = generateRepository;
  }

  public boolean isImplementSerializable() {
    return implementSerializable;
  }

  public void setImplementSerializable(boolean implementSerializable) {
    this.implementSerializable = implementSerializable;
  }

  public boolean isGenerateClassComment() {
    return generateClassComment;
  }

  public void setGenerateClassComment(boolean generateClassComment) {
    this.generateClassComment = generateClassComment;
  }

  public boolean isGenerateFieldComment() {
    return generateFieldComment;
  }

  public void setGenerateFieldComment(boolean generateFieldComment) {
    this.generateFieldComment = generateFieldComment;
  }

  public boolean isGenerateMethodComment() {
    return generateMethodComment;
  }

  public void setGenerateMethodComment(boolean generateMethodComment) {
    this.generateMethodComment = generateMethodComment;
  }

  public Set<String> getExcludeFields() {
    return excludeFields;
  }

  public GeneratorConfig setExcludeFields(Set<String> excludeFields) {
    this.excludeFields = excludeFields;
    return this;
  }

  public String getProjectBasePath() {
    return projectBasePath;
  }

  public GeneratorConfig setProjectBasePath(String projectBasePath) {
    this.projectBasePath = projectBasePath;
    return this;
  }

  public String getEntityDirectory() {
    return entityDirectory;
  }

  public GeneratorConfig setEntityDirectory(String entityDirectory) {
    this.entityDirectory = entityDirectory;
    return this;
  }

  public String getRepositoryDirectory() {
    return repositoryDirectory;
  }

  public GeneratorConfig setRepositoryDirectory(String repositoryDirectory) {
    this.repositoryDirectory = repositoryDirectory;
    return this;
  }

  public boolean isUseJava8DataType() {
    return useJava8DataType;
  }

  public GeneratorConfig setUseJava8DataType(boolean useJava8DataType) {
    this.useJava8DataType = useJava8DataType;
    return this;
  }

  @Override
  public String toString() {
    return "GeneratorConfig{" +
        "removeTablePrefix='" + removeTablePrefix + '\'' +
        ", removeFieldPrefix='" + removeFieldPrefix + '\'' +
        ", extendBaseClass='" + extendBaseClass + '\'' +
        ", entityPackage='" + entityPackage + '\'' +
        ", repositoryPackage='" + repositoryPackage + '\'' +
        ", excludeFields=" + excludeFields +
        ", projectBasePath='" + projectBasePath + '\'' +
        ", entityDirectory='" + entityDirectory + '\'' +
        ", repositoryDirectory='" + repositoryDirectory + '\'' +
        ", useLombok=" + useLombok +
        ", generateRepository=" + generateRepository +
        ", implementSerializable=" + implementSerializable +
        ", generateClassComment=" + generateClassComment +
        ", generateFieldComment=" + generateFieldComment +
        ", generateMethodComment=" + generateMethodComment +
        ", useJava8DataType=" + useJava8DataType +
        '}';
  }
}
