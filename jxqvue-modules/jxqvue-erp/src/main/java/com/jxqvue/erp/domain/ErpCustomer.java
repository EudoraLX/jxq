package com.jxqvue.erp.domain;

import java.math.BigDecimal;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.jxqvue.common.annotation.Excel;
import com.jxqvue.common.core.domain.BaseEntity;

/**
 * 客户对象 erp_customer
 *
 * @author Gavin Zhang
 * @date 2025-09-01
 */
public class ErpCustomer extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 客户ID
     */
    private Long id;

    /**
     * 客户编码
     */
    @Excel(name = "客户编码")
    private String customerCode;

    /**
     * 客户名称
     */
    @Excel(name = "客户名称")
    private String customerName;

    /**
     * 客户简称
     */
    @Excel(name = "客户简称")
    private String customerShortName;

    /**
     * 客户类型
     */
    @Excel(name = "客户类型")
    private String customerType;

    /**
     * 所属行业
     */
    @Excel(name = "所属行业")
    private String industry;

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
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "成立日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date establishmentDate;

    /**
     * 经营范围
     */
    private String businessScope;

    /**
     * 联系人
     */
    @Excel(name = "联系人")
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
     * 信用等级
     */
    @Excel(name = "信用等级")
    private String creditRating;

    /**
     * 信用额度
     */
    private BigDecimal creditLimit;

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

    /**
     * 是否启用
     */
    @Excel(name = "是否启用")
    private String isActive;

    private String delFlag;

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setCustomerCode(String customerCode) {
        this.customerCode = customerCode;
    }

    public String getCustomerCode() {
        return customerCode;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerShortName(String customerShortName) {
        this.customerShortName = customerShortName;
    }

    public String getCustomerShortName() {
        return customerShortName;
    }

    public void setCustomerType(String customerType) {
        this.customerType = customerType;
    }

    public String getCustomerType() {
        return customerType;
    }

    public void setIndustry(String industry) {
        this.industry = industry;
    }

    public String getIndustry() {
        return industry;
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

    public void setCreditLimit(BigDecimal creditLimit) {
        this.creditLimit = creditLimit;
    }

    public BigDecimal getCreditLimit() {
        return creditLimit;
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
                .append("customerCode", getCustomerCode())
                .append("customerName", getCustomerName())
                .append("customerShortName", getCustomerShortName())
                .append("customerType", getCustomerType())
                .append("industry", getIndustry())
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
                .append("creditLimit", getCreditLimit())
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
