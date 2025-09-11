package com.jxqvue.erp.domain;

import java.math.BigDecimal;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.jxqvue.common.annotation.Excel;
import com.jxqvue.common.core.domain.BaseEntity;

/**
 * 供应商对象 erp_supplier
 *
 * @author Gavin Zhang
 * @date 2025-09-01
 */
public class ErpSupplier extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 供应商ID
     */
    private Long id;

    /**
     * 供应商编码
     */
    @Excel(name = "供应商编码")
    private String supplierCode;

    /**
     * 供应商名称
     */
    @Excel(name = "供应商名称")
    private String supplierName;

    /**
     * 供应商简称
     */
    @Excel(name = "供应商简称")
    private String supplierShortName;

    /**
     * 供应商类型
     */
    @Excel(name = "供应商类型")
    private String supplierType;

    /**
     * 营业执照号
     */
    private String businessLicense;

    /**
     * 税务登记号
     */
    private String taxRegistrationNumber;

    /**
     * 法定代表人
     */
    private String legalRepresentative;

    /**
     * 注册资本
     */
    private BigDecimal registeredCapital;

    /**
     * 成立日期
     */
    private Date establishmentDate;

    /**
     * 经营范围
     */
    private String businessScope;

    /**
     * 联系人
     */
    private String contactPerson;

    /**
     * 联系电话
     */
    private String contactPhone;

    /**
     * 联系邮箱
     */
    private String contactEmail;

    /**
     * 地址
     */
    private String address;

    /**
     * 邮政编码
     */
    private String postalCode;

    /**
     * 开户行
     */
    private String bankName;

    /**
     * 银行账号
     */
    private String bankAccount;

    /**
     * 账户名称
     */
    private String accountName;

    /**
     * 信用等级（A优秀 B良好 C一般 D较差 E很差）
     */
    @Excel(name = "信用等级", readConverterExp = "A=优秀,B=良好,C=一般,D=较差,E=很差")
    private String creditRating;

    /**
     * 付款条件
     */
    private String paymentTerms;

    /**
     * 交货条件
     */
    private String deliveryTerms;

    /**
     * 质量评级
     */
    private BigDecimal qualityRating;

    /**
     * 交付评级
     */
    private BigDecimal deliveryRating;

    /**
     * 服务评级
     */
    private BigDecimal serviceRating;

    /**
     * 综合评级
     */
    private BigDecimal overallRating;

    private String delFlag;

    /**
     * 是否启用
     */
    @Excel(name = "是否启用")
    private String isActive;

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setSupplierCode(String supplierCode) {
        this.supplierCode = supplierCode;
    }

    public String getSupplierCode() {
        return supplierCode;
    }

    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
    }

    public String getSupplierName() {
        return supplierName;
    }

    public void setSupplierShortName(String supplierShortName) {
        this.supplierShortName = supplierShortName;
    }

    public String getSupplierShortName() {
        return supplierShortName;
    }

    public void setSupplierType(String supplierType) {
        this.supplierType = supplierType;
    }

    public String getSupplierType() {
        return supplierType;
    }

    public void setBusinessLicense(String businessLicense) {
        this.businessLicense = businessLicense;
    }

    public String getBusinessLicense() {
        return businessLicense;
    }

    public void setTaxRegistrationNumber(String taxRegistrationNumber) {
        this.taxRegistrationNumber = taxRegistrationNumber;
    }

    public String getTaxRegistrationNumber() {
        return taxRegistrationNumber;
    }

    public void setLegalRepresentative(String legalRepresentative) {
        this.legalRepresentative = legalRepresentative;
    }

    public String getLegalRepresentative() {
        return legalRepresentative;
    }

    public void setRegisteredCapital(BigDecimal registeredCapital) {
        this.registeredCapital = registeredCapital;
    }

    public BigDecimal getRegisteredCapital() {
        return registeredCapital;
    }

    public void setEstablishmentDate(Date establishmentDate) {
        this.establishmentDate = establishmentDate;
    }

    public Date getEstablishmentDate() {
        return establishmentDate;
    }

    public void setBusinessScope(String businessScope) {
        this.businessScope = businessScope;
    }

    public String getBusinessScope() {
        return businessScope;
    }

    public void setContactPerson(String contactPerson) {
        this.contactPerson = contactPerson;
    }

    public String getContactPerson() {
        return contactPerson;
    }

    public void setContactPhone(String contactPhone) {
        this.contactPhone = contactPhone;
    }

    public String getContactPhone() {
        return contactPhone;
    }

    public void setContactEmail(String contactEmail) {
        this.contactEmail = contactEmail;
    }

    public String getContactEmail() {
        return contactEmail;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAddress() {
        return address;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankAccount(String bankAccount) {
        this.bankAccount = bankAccount;
    }

    public String getBankAccount() {
        return bankAccount;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setCreditRating(String creditRating) {
        this.creditRating = creditRating;
    }

    public String getCreditRating() {
        return creditRating;
    }

    public void setPaymentTerms(String paymentTerms) {
        this.paymentTerms = paymentTerms;
    }

    public String getPaymentTerms() {
        return paymentTerms;
    }

    public void setDeliveryTerms(String deliveryTerms) {
        this.deliveryTerms = deliveryTerms;
    }

    public String getDeliveryTerms() {
        return deliveryTerms;
    }

    public void setQualityRating(BigDecimal qualityRating) {
        this.qualityRating = qualityRating;
    }

    public BigDecimal getQualityRating() {
        return qualityRating;
    }

    public void setDeliveryRating(BigDecimal deliveryRating) {
        this.deliveryRating = deliveryRating;
    }

    public BigDecimal getDeliveryRating() {
        return deliveryRating;
    }

    public void setServiceRating(BigDecimal serviceRating) {
        this.serviceRating = serviceRating;
    }

    public BigDecimal getServiceRating() {
        return serviceRating;
    }

    public void setOverallRating(BigDecimal overallRating) {
        this.overallRating = overallRating;
    }

    public BigDecimal getOverallRating() {
        return overallRating;
    }

    public void setIsActive(String isActive) {
        this.isActive = isActive;
    }

    public String getIsActive() {
        return isActive;
    }

    public String getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(String delFlag) {
        this.delFlag = delFlag;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("id", getId())
                .append("supplierCode", getSupplierCode())
                .append("supplierName", getSupplierName())
                .append("supplierShortName", getSupplierShortName())
                .append("supplierType", getSupplierType())
                .append("businessLicense", getBusinessLicense())
                .append("taxRegistrationNumber", getTaxRegistrationNumber())
                .append("legalRepresentative", getLegalRepresentative())
                .append("registeredCapital", getRegisteredCapital())
                .append("establishmentDate", getEstablishmentDate())
                .append("businessScope", getBusinessScope())
                .append("contactPerson", getContactPerson())
                .append("contactPhone", getContactPhone())
                .append("contactEmail", getContactEmail())
                .append("address", getAddress())
                .append("postalCode", getPostalCode())
                .append("bankName", getBankName())
                .append("bankAccount", getBankAccount())
                .append("accountName", getAccountName())
                .append("creditRating", getCreditRating())
                .append("paymentTerms", getPaymentTerms())
                .append("deliveryTerms", getDeliveryTerms())
                .append("qualityRating", getQualityRating())
                .append("deliveryRating", getDeliveryRating())
                .append("serviceRating", getServiceRating())
                .append("overallRating", getOverallRating())
                .append("isActive", getIsActive())
                .append("remark", getRemark())
                .append("createBy", getCreateBy())
                .append("createTime", getCreateTime())
                .append("updateBy", getUpdateBy())
                .append("updateTime", getUpdateTime())
                .append("delFlag", getDelFlag())
                .toString();
    }
}
