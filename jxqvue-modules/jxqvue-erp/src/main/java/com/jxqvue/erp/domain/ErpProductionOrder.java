package com.jxqvue.erp.domain;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.jxqvue.common.annotation.Excel;
import com.jxqvue.common.core.domain.BaseEntity;

/**
 * 生产订单对象 erp_production_order
 *
 * @author jxqvue
 * @date 2024-12-19
 */
public class ErpProductionOrder extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 订单ID
     */
    private Long id;

    /**
     * 订单编码
     */
    @Excel(name = "订单编码")
    private String orderCode;

    /**
     * 订单名称
     */
    @Excel(name = "订单名称")
    private String orderName;

    /**
     * 订单类型
     */
    @Excel(name = "订单类型")
    private String orderType;

    /**
     * 产品物料ID
     */
    @Excel(name = "产品物料ID")
    private Long productId;

    /**
     * BOM编号
     */
    @Excel(name = "BOM编号")
    private Long bomId;

    /**
     * 工艺路线ID
     */
    @Excel(name = "工艺路线ID")
    private Long processRouteId;

    /**
     * 单位ID
     */
    @Excel(name = "单位ID")
    private Long unitId;

    /**
     * 计划数量
     */
    @Excel(name = "计划数量")
    private BigDecimal plannedQuantity;

    /**
     * 完成数量
     */
    @Excel(name = "完成数量")
    private BigDecimal completedQuantity;

    /**
     * 报废数量
     */
    @Excel(name = "报废数量")
    private BigDecimal scrappedQuantity;

    /**
     * 剩余数量
     */
    @Excel(name = "剩余数量")
    private BigDecimal remainingQuantity;

    /**
     * 计划开始时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "计划开始时间", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss")
    private Date plannedStartDate;

    /**
     * 计划完成时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "计划完成时间", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss")
    private Date plannedEndDate;

    /**
     * 实际开始时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "实际开始时间", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss")
    private Date actualStartDate;

    /**
     * 实际完成时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "实际完成时间", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss")
    private Date actualEndDate;

    /**
     * 优先级
     */
    @Excel(name = "优先级")
    private Integer priority;

    /**
     * 状态（0草稿 1已确认 2生产中 3已完成 4已取消）
     */
    @Excel(name = "状态", readConverterExp = "0=草稿,1=已确认,2=生产中,3=已完成,4=已取消")
    private String status;


    private String productCode;
    private String productName;
    private String bomCode;
    private String processRouteCode;
    private String processRouteName;
    private String unitCode;
    private String unitName;

    /**
     * 生产订单明细信息
     */
    private List<ErpProductionOrderDetail> erpProductionOrderDetailList;

    /**
     * 生产订单物料信息
     */
    private List<ErpProductionOrderMaterials> erpProductionOrderMaterialsList;

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setOrderCode(String orderCode) {
        this.orderCode = orderCode;
    }

    public String getOrderCode() {
        return orderCode;
    }

    public void setOrderName(String orderName) {
        this.orderName = orderName;
    }

    public String getOrderName() {
        return orderName;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }

    public String getOrderType() {
        return orderType;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public Long getProductId() {
        return productId;
    }

    public void setBomId(Long bomId) {
        this.bomId = bomId;
    }

    public Long getBomId() {
        return bomId;
    }

    public void setProcessRouteId(Long processRouteId) {
        this.processRouteId = processRouteId;
    }

    public Long getProcessRouteId() {
        return processRouteId;
    }

    public void setUnitId(Long unitId) {
        this.unitId = unitId;
    }

    public Long getUnitId() {
        return unitId;
    }

    public void setPlannedQuantity(BigDecimal plannedQuantity) {
        this.plannedQuantity = plannedQuantity;
    }

    public BigDecimal getPlannedQuantity() {
        return plannedQuantity;
    }

    public void setCompletedQuantity(BigDecimal completedQuantity) {
        this.completedQuantity = completedQuantity;
    }

    public BigDecimal getCompletedQuantity() {
        return completedQuantity;
    }

    public void setScrappedQuantity(BigDecimal scrappedQuantity) {
        this.scrappedQuantity = scrappedQuantity;
    }

    public BigDecimal getScrappedQuantity() {
        return scrappedQuantity;
    }

    public void setRemainingQuantity(BigDecimal remainingQuantity) {
        this.remainingQuantity = remainingQuantity;
    }

    public BigDecimal getRemainingQuantity() {
        return remainingQuantity;
    }

    public void setPlannedStartDate(Date plannedStartDate) {
        this.plannedStartDate = plannedStartDate;
    }

    public Date getPlannedStartDate() {
        return plannedStartDate;
    }

    public void setPlannedEndDate(Date plannedEndDate) {
        this.plannedEndDate = plannedEndDate;
    }

    public Date getPlannedEndDate() {
        return plannedEndDate;
    }

    public void setActualStartDate(Date actualStartDate) {
        this.actualStartDate = actualStartDate;
    }

    public Date getActualStartDate() {
        return actualStartDate;
    }

    public void setActualEndDate(Date actualEndDate) {
        this.actualEndDate = actualEndDate;
    }

    public Date getActualEndDate() {
        return actualEndDate;
    }

    public void setPriority(Integer priority) {
        this.priority = priority;
    }

    public Integer getPriority() {
        return priority;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    public List<ErpProductionOrderDetail> getErpProductionOrderDetailList() {
        return erpProductionOrderDetailList;
    }

    public void setErpProductionOrderDetailList(List<ErpProductionOrderDetail> erpProductionOrderDetailList) {
        this.erpProductionOrderDetailList = erpProductionOrderDetailList;
    }

    public List<ErpProductionOrderMaterials> getErpProductionOrderMaterialsList() {
        return erpProductionOrderMaterialsList;
    }

    public void setErpProductionOrderMaterialsList(List<ErpProductionOrderMaterials> erpProductionOrderMaterialsList) {
        this.erpProductionOrderMaterialsList = erpProductionOrderMaterialsList;
    }


    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getBomCode() {
        return bomCode;
    }

    public void setBomCode(String bomCode) {
        this.bomCode = bomCode;
    }

    public String getProcessRouteCode() {
        return processRouteCode;
    }

    public void setProcessRouteCode(String processRouteCode) {
        this.processRouteCode = processRouteCode;
    }

    public String getUnitCode() {
        return unitCode;
    }

    public void setUnitCode(String unitCode) {
        this.unitCode = unitCode;
    }

    public String getProcessRouteName() {
        return processRouteName;
    }

    public void setProcessRouteName(String processRouteName) {
        this.processRouteName = processRouteName;
    }

    public String getUnitName() {
        return unitName;
    }

    public void setUnitName(String unitName) {
        this.unitName = unitName;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("id", getId())
                .append("orderCode", getOrderCode())
                .append("orderName", getOrderName())
                .append("orderType", getOrderType())
                .append("productId", getProductId())
                .append("bomId", getBomId())
                .append("processRouteId", getProcessRouteId())
                .append("unitId", getUnitId())
                .append("plannedQuantity", getPlannedQuantity())
                .append("completedQuantity", getCompletedQuantity())
                .append("scrappedQuantity", getScrappedQuantity())
                .append("remainingQuantity", getRemainingQuantity())
                .append("plannedStartDate", getPlannedStartDate())
                .append("plannedEndDate", getPlannedEndDate())
                .append("actualStartDate", getActualStartDate())
                .append("actualEndDate", getActualEndDate())
                .append("priority", getPriority())
                .append("status", getStatus())
                .append("remark", getRemark())
                .append("createBy", getCreateBy())
                .append("createTime", getCreateTime())
                .append("updateBy", getUpdateBy())
                .append("updateTime", getUpdateTime())
                .append("productCode", getProductCode())
                .append("productName", getProductName())
                .append("bomCode", getBomCode())
                .append("processRouteCode", getProcessRouteCode())
                .append("processRouteName", getProcessRouteName())
                .append("unitCode", getUnitCode())
                .append("unitName", getUnitName())
                .append("erpProductionOrderDetailList", getErpProductionOrderDetailList())
                .append("erpProductionOrderMaterialsList", getErpProductionOrderMaterialsList())
                .toString();
    }
}
