<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="80px">
      <el-form-item label="订单编码" prop="orderCode">
        <el-input
          v-model="queryParams.orderCode"
          placeholder="请输入订单编码"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="订单名称" prop="orderName">
        <el-input
          v-model="queryParams.orderName"
          placeholder="请输入订单名称"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="订单类型" prop="orderType">
        <el-select v-model="queryParams.orderType" placeholder="请选择订单类型" clearable>
          <el-option
            v-for="dict in dict.type.production_order_type"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="状态" prop="status">
        <el-select v-model="queryParams.status" placeholder="请选择状态" clearable>
          <el-option
            v-for="dict in dict.type.production_order_status"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="计划开始">
        <el-date-picker
          v-model="daterangePlannedStartDate"
          style="width: 240px"
          value-format="yyyy-MM-dd"
          type="daterange"
          range-separator="-"
          start-placeholder="开始日期"
          end-placeholder="结束日期"
        ></el-date-picker>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button
          type="primary"
          plain
          icon="el-icon-plus"
          size="mini"
          @click="handleAdd"
          v-hasPermi="['erp:productionOrder:add']"
        >新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          plain
          icon="el-icon-edit"
          size="mini"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['erp:productionOrder:edit']"
        >修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          plain
          icon="el-icon-delete"
          size="mini"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['erp:productionOrder:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['erp:productionOrder:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="productionOrderList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="订单编码" align="center" prop="orderCode" />
      <el-table-column label="订单名称" align="center" prop="orderName" />
      <el-table-column label="订单类型" align="center" prop="orderType">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.production_order_type" :value="scope.row.orderType"/>
        </template>
      </el-table-column>
      <el-table-column label="产品物料" align="center" prop="productId" width="200">
        <template slot-scope="scope">
          <span v-if="scope.row.productCode && scope.row.productName">
            {{ scope.row.productCode }} - {{ scope.row.productName }}
          </span>
          <span v-else-if="scope.row.productCode">
            {{ scope.row.productCode }}
          </span>
          <span v-else>
            {{ scope.row.productId }}
          </span>
        </template>
      </el-table-column>
      <el-table-column label="计划数量" align="center" prop="plannedQuantity" />
      <el-table-column label="完成数量" align="center" prop="completedQuantity" />
      <el-table-column label="剩余数量" align="center" prop="remainingQuantity" />
      <el-table-column label="单位" align="center" prop="unitName" />
      <el-table-column label="计划开始时间" align="center" prop="plannedStartDate" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.plannedStartDate, '{y}-{m}-{d} {h}:{i}:{s}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="计划完成时间" align="center" prop="plannedEndDate" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.plannedEndDate, '{y}-{m}-{d} {h}:{i}:{s}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="优先级" align="center" prop="priority" />
      <el-table-column label="状态" align="center" prop="status">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.production_order_status" :value="scope.row.status"/>
        </template>
      </el-table-column>
      <el-table-column label="条码" align="center" width="120">
        <template slot-scope="scope">
          <barcode-display
            :value="scope.row.orderCode"
            :width="1.5"
            :height="40"
            format="CODE128"
          />
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width" width="250">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['erp:productionOrder:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['erp:productionOrder:remove']"
          >删除</el-button>
          <!-- 提交按钮：用户有edit权限且状态为0时显示 -->
          <el-button
            size="mini"
            type="text"
            icon="el-icon-check"
            @click="handleSubmit(scope.row)"
            v-hasPermi="['erp:productionOrder:edit']"
            v-if="scope.row.status === '0'"
          >提交</el-button>
          <!-- 下达按钮：用户有approval权限且状态为1时显示 -->
          <el-button
            size="mini"
            type="text"
            icon="el-icon-s-promotion"
            @click="handleRelease(scope.row)"
            v-hasPermi="['erp:productionOrder:approval']"
            v-if="scope.row.status === '1'"
          >下达</el-button>
          <!-- 关闭按钮：用户有approval权限且状态为2或3时显示 -->
          <el-button
            size="mini"
            type="text"
            icon="el-icon-close"
            @click="handleClose(scope.row)"
            v-hasPermi="['erp:productionOrder:approval']"
            v-if="scope.row.status === '2' || scope.row.status === '3'"
          >关闭</el-button>
          <!-- 条码打印按钮 -->
          <barcode-printer
            :machine-data="getProductionOrderBarcodeData(scope.row)"
            title="生产订单标签"
            label-title="生产订单标签"
            :info-labels="productionOrderBarcodeLabels"
            :machine-type-dict="[]"
          />
        </template>
      </el-table-column>
    </el-table>

    <pagination
      v-show="total>0"
      :total="total"
      :page.sync="queryParams.pageNum"
      :limit.sync="queryParams.pageSize"
      @pagination="getList"
    />

    <!-- 添加或修改生产订单对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="90%" append-to-body>
      <el-tabs v-model="activeTab" type="border-card">
        <!-- 基本信息Tab -->
        <el-tab-pane label="基本信息" name="basic">
          <!-- 加载明细按钮 -->
          <el-row :gutter="10" class="mb8" style="padding: 10px 20px 0 20px;">
            <el-col :span="1.5">
              <el-button
                type="success"
                icon="el-icon-download"
                size="mini"
                @click="handleLoadDetails"
                :loading="loadingDetails"
              >加载明细</el-button>
            </el-col>
            <el-col :span="1.5">
              <el-button
                type="warning"
                icon="el-icon-refresh-left"
                size="mini"
                @click="handleClearDetails"
              >清空明细</el-button>
            </el-col>
          </el-row>
          <el-form ref="form" :model="form" :rules="rules" label-width="120px" style="padding: 20px;">
            <el-row :gutter="20">
              <el-col :span="12">
                <el-form-item label="订单编码" prop="orderCode">
                  <el-input
                    v-model="form.orderCode"
                    :placeholder="form.id ? '订单编码' : '系统自动生成'"
                    readonly
                    prefix-icon="el-icon-document"
                    style="background-color: #f5f7fa;"
                  />
                </el-form-item>
              </el-col>
              <el-col :span="12">
                <el-form-item label="订单名称" prop="orderName">
                  <el-input v-model="form.orderName" placeholder="请输入订单名称" prefix-icon="el-icon-edit" />
                </el-form-item>
              </el-col>
            </el-row>
            <el-row :gutter="20">
              <el-col :span="12">
                <el-form-item label="订单类型" prop="orderType">
                  <el-select v-model="form.orderType" placeholder="请选择订单类型" style="width: 100%;">
                    <el-option
                      v-for="dict in dict.type.production_order_type"
                      :key="dict.value"
                      :label="dict.label"
                      :value="dict.value"
                    ></el-option>
                  </el-select>
                </el-form-item>
              </el-col>
              <el-col :span="12">
                <el-form-item label="产品物料" prop="productId">
                  <div style="display: flex; align-items: center;">
                    <div style="flex: 1; position: relative;" @click="selectProduct">
                      <el-input
                        v-model="form.productCode"
                        placeholder="请选择产品物料"
                        readonly
                        style="background-color: #f5f7fa; cursor: pointer;"
                        prefix-icon="el-icon-goods"
                      />
                    </div>
                    <el-button
                      type="primary"
                      icon="el-icon-arrow-down"
                      size="mini"
                      @click="selectProduct"
                      style="margin-left: 10px;"
                    >
                      选择
                    </el-button>
                    <el-button
                      v-if="form.productId"
                      type="danger"
                      icon="el-icon-delete"
                      size="mini"
                      @click="clearProduct"
                      style="margin-left: 5px;"
                    >
                      清除
                    </el-button>
                  </div>
                </el-form-item>
              </el-col>
            </el-row>
            <el-row :gutter="20">
              <el-col :span="12">
                <el-form-item label="BOM编号" prop="bomId">
                  <div style="display: flex; align-items: center;">
                    <div style="flex: 1; position: relative;" @click="selectBom">
                      <el-input
                        v-model="form.bomCode"
                        placeholder="请选择BOM编号"
                        readonly
                        style="background-color: #f5f7fa; cursor: pointer;"
                        prefix-icon="el-icon-s-operation"
                      />
                    </div>
                    <el-button
                      type="primary"
                      icon="el-icon-arrow-down"
                      size="mini"
                      @click="selectBom"
                      style="margin-left: 10px;"
                    >
                      选择
                    </el-button>
                    <el-button
                      v-if="form.bomId"
                      type="danger"
                      icon="el-icon-delete"
                      size="mini"
                      @click="clearBom"
                      style="margin-left: 5px;"
                    >
                      清除
                    </el-button>
                  </div>
                </el-form-item>
              </el-col>
              <el-col :span="12">
                <el-form-item label="工艺路线" prop="processRouteId">
                  <el-input v-model="form.processRouteName" placeholder="工艺路线" readonly prefix-icon="el-icon-s-operation" />
                </el-form-item>
              </el-col>
            </el-row>
            <el-row :gutter="20">
              <el-col :span="12">
                <el-form-item label="单位" prop="unitId">
                  <el-input v-model="form.unitName" placeholder="单位" readonly prefix-icon="el-icon-scale" />
                </el-form-item>
              </el-col>
              <el-col :span="12">
                <el-form-item label="计划数量" prop="plannedQuantity">
                  <el-input-number
                    v-model="form.plannedQuantity"
                    :precision="4"
                    :step="0.0001"
                    placeholder="请输入计划数量"
                    style="width: 100%;"
                    :min="0"
                  />
                </el-form-item>
              </el-col>
            </el-row>
            <el-row :gutter="20">
              <el-col :span="12">
                <el-form-item label="计划开始时间" prop="plannedStartDate">
                  <el-date-picker
                    clearable
                    v-model="form.plannedStartDate"
                    type="datetime"
                    value-format="yyyy-MM-dd HH:mm:ss"
                    placeholder="请选择计划开始时间"
                    style="width: 100%;"
                    prefix-icon="el-icon-calendar"
                  />
                </el-form-item>
              </el-col>
              <el-col :span="12">
                <el-form-item label="计划完成时间" prop="plannedEndDate">
                  <el-date-picker
                    clearable
                    v-model="form.plannedEndDate"
                    type="datetime"
                    value-format="yyyy-MM-dd HH:mm:ss"
                    placeholder="请选择计划完成时间"
                    style="width: 100%;"
                    prefix-icon="el-icon-calendar"
                  />
                </el-form-item>
              </el-col>
            </el-row>
            <el-row :gutter="20">
              <el-col :span="12">
                <el-form-item label="优先级" prop="priority">
                  <el-input-number
                    v-model="form.priority"
                    :min="0"
                    :max="999"
                    placeholder="请输入优先级"
                    style="width: 100%;"
                  />
                </el-form-item>
              </el-col>
              <el-col :span="12">
                <el-form-item label="状态" prop="status">
                  <el-select v-model="form.status" placeholder="请选择状态" style="width: 100%;">
                    <el-option
                      v-for="dict in dict.type.production_order_status"
                      :key="dict.value"
                      :label="dict.label"
                      :value="dict.value"
                    ></el-option>
                  </el-select>
                </el-form-item>
              </el-col>
            </el-row>
            <el-row :gutter="20">
              <el-col :span="24">
                <el-form-item label="备注" prop="remark">
                  <el-input v-model="form.remark" type="textarea" placeholder="请输入备注信息" />
                </el-form-item>
              </el-col>
            </el-row>
          </el-form>
        </el-tab-pane>

        <!-- 工艺路线明细信息Tab -->
        <el-tab-pane label="工艺路线明细信息" name="orderDetail">
          <el-row :gutter="10" class="mb8">
            <el-col :span="1.5">
              <el-button type="primary" icon="el-icon-plus" size="mini" @click="handleAddOrderDetail">添加</el-button>
            </el-col>
            <el-col :span="1.5">
              <el-button type="danger" icon="el-icon-delete" size="mini" @click="handleDeleteOrderDetail">删除</el-button>
            </el-col>
          </el-row>
          <el-table :data="orderDetailList" :row-class-name="rowOrderDetailIndex" @selection-change="handleOrderDetailSelectionChange" ref="orderDetail">
            <el-table-column type="selection" width="50" align="center" />
            <el-table-column label="序号" align="center" prop="index" width="50"/>
            <el-table-column label="工序" prop="processId" width="150">
              <template slot-scope="scope">
                <el-input v-model="scope.row.processName" placeholder="请选择工序" readonly @click="selectProcess(scope.$index)">
                  <el-button slot="append" icon="el-icon-search" @click="selectProcess(scope.$index)"></el-button>
                </el-input>
              </template>
            </el-table-column>
            <el-table-column label="工序顺序" prop="processOrder" width="120">
              <template slot-scope="scope">
                <el-input v-model="scope.row.processOrder" placeholder="请输入工序顺序" />
              </template>
            </el-table-column>
            <el-table-column label="工作中心" prop="workCenterId" width="150">
              <template slot-scope="scope">
                <el-input v-model="scope.row.workCenterName" placeholder="请选择工作中心" readonly @click="selectWorkCenter(scope.$index)">
                  <el-button slot="append" icon="el-icon-search" @click="selectWorkCenter(scope.$index)"></el-button>
                </el-input>
              </template>
            </el-table-column>
            <el-table-column label="设备" prop="equipmentId" width="150">
              <template slot-scope="scope">
                <el-input v-model="scope.row.equipmentName" placeholder="请选择设备" readonly @click="selectEquipment(scope.$index)">
                  <el-button slot="append" icon="el-icon-search" @click="selectEquipment(scope.$index)"></el-button>
                </el-input>
              </template>
            </el-table-column>
            <el-table-column label="模具" prop="moldId" width="150">
              <template slot-scope="scope">
                <el-input v-model="scope.row.moldName" placeholder="请选择模具" readonly @click="selectMold(scope.$index)">
                  <el-button slot="append" icon="el-icon-search" @click="selectMold(scope.$index)"></el-button>
                </el-input>
              </template>
            </el-table-column>
            <el-table-column label="计划工时" prop="plannedDuration" width="120">
              <template slot-scope="scope">
                <el-input v-model="scope.row.plannedDuration" placeholder="请输入计划工时" />
              </template>
            </el-table-column>
            <el-table-column label="操作人员" prop="operatorId" width="150">
              <template slot-scope="scope">
                <el-input v-model="scope.row.operatorName" placeholder="请选择操作人员" readonly @click="selectOperator(scope.$index)">
                  <el-button slot="append" icon="el-icon-search" @click="selectOperator(scope.$index)"></el-button>
                </el-input>
              </template>
            </el-table-column>

          </el-table>
        </el-tab-pane>

        <!-- 生产订单物料信息Tab -->
        <el-tab-pane label="生产订单物料信息" name="orderMaterials">
          <el-row :gutter="10" class="mb8">
            <el-col :span="1.5">
              <el-button type="primary" icon="el-icon-plus" size="mini" @click="handleAddOrderMaterials">添加</el-button>
            </el-col>
            <el-col :span="1.5">
              <el-button type="danger" icon="el-icon-delete" size="mini" @click="handleDeleteOrderMaterials">删除</el-button>
            </el-col>
          </el-row>
          <el-table :data="orderMaterialsList" :row-class-name="rowOrderMaterialsIndex" @selection-change="handleOrderMaterialsSelectionChange" ref="orderMaterials">
            <el-table-column type="selection" width="50" align="center" />
            <el-table-column label="序号" align="center" prop="index" width="50"/>
            <el-table-column label="物料编号" prop="materialId" width="200">
              <template slot-scope="scope">
                <el-input v-model="scope.row.materialName" placeholder="请选择物料编号" readonly @click="selectMaterial(scope.$index)">
                  <el-button slot="append" icon="el-icon-search" @click="selectMaterial(scope.$index)"></el-button>
                </el-input>
              </template>
            </el-table-column>
            <el-table-column label="数量" prop="quantity" width="120">
              <template slot-scope="scope">
                <el-input v-model="scope.row.quantity" placeholder="请输入数量" />
              </template>
            </el-table-column>
            <el-table-column label="发料数量" prop="issuedQty" width="120">
              <template slot-scope="scope">
                <el-input v-model="scope.row.issuedQty" placeholder="发料数量" readonly />
              </template>
            </el-table-column>
            <el-table-column label="待发数量" prop="needQty" width="120">
              <template slot-scope="scope">
                <el-input v-model="scope.row.needQty" placeholder="待发数量" readonly />
              </template>
            </el-table-column>
            <el-table-column label="单位" prop="unitId" width="120">
              <template slot-scope="scope">
                <el-input v-model="scope.row.unitName" placeholder="单位" readonly />
              </template>
            </el-table-column>
            <el-table-column label="备注" prop="remark" width="200">
              <template slot-scope="scope">
                <el-input v-model="scope.row.remark" placeholder="请输入备注" />
              </template>
            </el-table-column>
          </el-table>
        </el-tab-pane>
      </el-tabs>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>

    <!-- 物料选择组件 -->
    <material-select ref="materialSelect" :only-active="true" @select="handleMaterialSelect" />
    <!-- 生产订单物料选择组件（多选模式） -->
    <material-select ref="orderMaterialsMaterialSelect" :only-active="true" :multiple="true" @select="handleOrderMaterialsMaterialSelect" />

    <!-- BOM选择对话框 -->
    <el-dialog title="选择BOM" :visible.sync="bomDialogVisible" width="800px" append-to-body>
      <el-table :data="bomList" @row-click="selectBomRow">
        <el-table-column label="BOM编码" prop="bomCode" />
        <el-table-column label="BOM名称" prop="bomName" />
        <el-table-column label="版本号" prop="version" />
        <el-table-column label="产品编号" prop="productCode" />
      </el-table>
      <div slot="footer" class="dialog-footer">
        <el-button @click="bomDialogVisible = false">取 消</el-button>
      </div>
    </el-dialog>

    <!-- 工序选择对话框 -->
    <el-dialog title="选择工序" :visible.sync="processDialogVisible" width="600px" append-to-body>
      <el-table :data="processList" @row-click="selectProcessRow">
        <el-table-column label="工序编码" prop="processCode" />
        <el-table-column label="工序名称" prop="processName" />
      </el-table>
      <div slot="footer" class="dialog-footer">
        <el-button @click="processDialogVisible = false">取 消</el-button>
      </div>
    </el-dialog>

    <!-- 工作中心选择对话框 -->
    <el-dialog title="选择工作中心" :visible.sync="workCenterDialogVisible" width="600px" append-to-body>
      <el-table :data="workCenterList" @row-click="selectWorkCenterRow">
        <el-table-column label="工作中心编码" prop="workCenterCode" />
        <el-table-column label="工作中心名称" prop="workCenterName" />
      </el-table>
      <div slot="footer" class="dialog-footer">
        <el-button @click="workCenterDialogVisible = false">取 消</el-button>
      </div>
    </el-dialog>

    <!-- 设备选择对话框 -->
    <el-dialog title="选择设备" :visible.sync="equipmentDialogVisible" width="600px" append-to-body>
      <el-table :data="equipmentList" @row-click="selectEquipmentRow">
        <el-table-column label="设备编码" prop="equipmentCode" />
        <el-table-column label="设备名称" prop="equipmentName" />
      </el-table>
      <div slot="footer" class="dialog-footer">
        <el-button @click="equipmentDialogVisible = false">取 消</el-button>
      </div>
    </el-dialog>

    <!-- 模具选择对话框 -->
    <el-dialog title="选择模具" :visible.sync="moldDialogVisible" width="600px" append-to-body>
      <el-table :data="moldList" @row-click="selectMoldRow">
        <el-table-column label="模具编码" prop="moldCode" />
        <el-table-column label="模具名称" prop="moldName" />
      </el-table>
      <div slot="footer" class="dialog-footer">
        <el-button @click="moldDialogVisible = false">取 消</el-button>
      </div>
    </el-dialog>

    <!-- 操作人员选择对话框 -->
    <el-dialog title="选择操作人员" :visible.sync="operatorDialogVisible" width="600px" append-to-body>
      <el-table :data="operatorList" @row-click="selectOperatorRow">
        <el-table-column label="用户ID" prop="userId" />
        <el-table-column label="用户昵称" prop="nickName" />
        <el-table-column label="用户名" prop="userName" />
      </el-table>
      <div slot="footer" class="dialog-footer">
        <el-button @click="operatorDialogVisible = false">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { listProductionOrder, getProductionOrder, delProductionOrder, addProductionOrder, updateProductionOrder, updateProductionOrderStatus } from "@/api/erp/productionOrder"
import { listMasterMaterial } from "@/api/erp/masterMaterial"
import { listBom, getBom } from "@/api/erp/bom"
import { listBomDetail } from "@/api/erp/bom"
import { listBomRouteDetail } from "@/api/erp/bom"
import { listProcessRouteDetailByRouteId } from "@/api/erp/processRouteDetail"
import { listProcess } from "@/api/erp/process"
import { listWorkCenter } from "@/api/erp/workCenter"
import { listEquipment } from "@/api/erp/equipment"
import { listMold } from "@/api/erp/mold"
import { listUser } from "@/api/system/user"
import MaterialSelect from "@/components/MaterialSelect"
import BarcodeDisplay from "@/components/BarcodeDisplay"
import BarcodePrinter from "@/components/BarcodePrinter"

export default {
  name: "ProductionOrder",
  components: {
    MaterialSelect,
    BarcodeDisplay,
    BarcodePrinter
  },
  dicts: ['production_order_type', 'production_order_status'],
  data() {
    return {
      // 遮罩层
      loading: true,
      // 选中数组
      ids: [],
      // 子表选中数据
      checkedOrderDetail: [],
      checkedOrderMaterials: [],
      // 非单个禁用
      single: true,
      // 非多个禁用
      multiple: true,
      // 显示搜索条件
      showSearch: true,
      // 总条数
      total: 0,
      // 生产订单表格数据
      productionOrderList: [],
      // 工艺路线明细表格数据
      orderDetailList: [],
      // 生产订单物料表格数据
      orderMaterialsList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 当前激活的Tab
      activeTab: 'basic',
      // 生产订单条码标签配置
      productionOrderBarcodeLabels: {
        code: '订单编码',
        name: '订单名称',
        type: '订单类型',
        workCenter: '产品物料'
      },
      // 计划开始时间范围
      daterangePlannedStartDate: [],
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        orderCode: null,
        orderName: null,
        orderType: null,
        status: null
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        orderName: [
          { required: true, message: "订单名称不能为空", trigger: "blur" }
        ],
        orderType: [
          { required: true, message: "订单类型不能为空", trigger: "change" }
        ],
        productId: [
          { required: true, message: "产品物料不能为空", trigger: "change" }
        ],
        plannedQuantity: [
          { required: true, message: "计划数量不能为空", trigger: "blur" }
        ],
        status: [
          { required: true, message: "状态不能为空", trigger: "change" }
        ]
      },
      // 对话框显示状态
      bomDialogVisible: false,
      processDialogVisible: false,
      workCenterDialogVisible: false,
      equipmentDialogVisible: false,
      moldDialogVisible: false,
      operatorDialogVisible: false,
      // 选择数据
      bomList: [],
      processList: [],
      workCenterList: [],
      equipmentList: [],
      moldList: [],
      operatorList: [],
      // 当前选择的行索引
      currentDetailIndex: -1,
      currentMaterialsIndex: -1,
      // 加载明细状态
      loadingDetails: false
    }
  },
  created() {
    this.getList()
  },
  methods: {
    /** 查询生产订单列表 */
    getList() {
      this.loading = true
      this.queryParams.params = {}
      if (null != this.daterangePlannedStartDate && '' != this.daterangePlannedStartDate) {
        this.queryParams.params["beginPlannedStartDate"] = this.daterangePlannedStartDate[0]
        this.queryParams.params["endPlannedStartDate"] = this.daterangePlannedStartDate[1]
      }
      listProductionOrder(this.queryParams).then(response => {
        this.productionOrderList = response.rows
        this.total = response.total
        this.loading = false
      })
    },
    // 取消按钮
    cancel() {
      this.open = false
      this.reset()
    },
    // 表单重置
    reset() {
      this.form = {
        id: null,
        orderCode: null,
        orderName: null,
        orderType: null,
        productId: null,
        productCode: null,
        productName: null,
        bomId: null,
        bomCode: null,
        processRouteId: null,
        processRouteCode: null,
        processRouteName: null,
        unitId: null,
        unitCode: null,
        unitName: null,
        plannedQuantity: null,
        completedQuantity: 0,
        scrappedQuantity: 0,
        remainingQuantity: 0,
        plannedStartDate: null,
        plannedEndDate: null,
        actualStartDate: null,
        actualEndDate: null,
        priority: 0,
        status: "0",
        remark: null
      }
      this.orderDetailList = []
      this.orderMaterialsList = []
      this.resetForm("form")
    },
    /** 搜索按钮操作 */
    handleQuery() {
      this.queryParams.pageNum = 1
      this.getList()
    },
    /** 重置按钮操作 */
    resetQuery() {
      this.daterangePlannedStartDate = []
      this.resetForm("queryForm")
      this.handleQuery()
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.id)
      this.single = selection.length!==1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset()
      this.open = true
      this.title = "添加生产订单"
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset()
      const id = row.id || this.ids
      getProductionOrder(id).then(response => {
        this.form = response.data
        this.orderDetailList = response.data.erpProductionOrderDetailList || []
        this.orderMaterialsList = response.data.erpProductionOrderMaterialsList || []
        this.open = true
        this.title = "修改生产订单"
      })
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          this.form.erpProductionOrderDetailList = this.orderDetailList
          this.form.erpProductionOrderMaterialsList = this.orderMaterialsList

          if (this.form.id != null) {
            updateProductionOrder(this.form).then(response => {
              this.$modal.msgSuccess("修改成功")
              this.open = false
              this.getList()
            })
          } else {
            addProductionOrder(this.form).then(response => {
              this.$modal.msgSuccess("新增成功")
              this.open = false
              this.getList()
            })
          }
        }
      })
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const ids = row.id || this.ids
      this.$modal.confirm('是否确认删除生产订单编号为"' + ids + '"的数据项？').then(function() {
        return delProductionOrder(ids)
      }).then(() => {
        this.getList()
        this.$modal.msgSuccess("删除成功")
      }).catch(() => {})
    },
    /** 工艺路线明细序号 */
    rowOrderDetailIndex({ row, rowIndex }) {
      row.index = rowIndex + 1
    },
    /** 工艺路线明细添加按钮操作 */
    handleAddOrderDetail() {
      let obj = {
        processId: null,
        processOrder: 0,
        workCenterId: null,
        equipmentId: null,
        moldId: null,
        plannedDuration: 0,
        actualDuration: 0,
        operatorId: null,
        moldSetupTime: 0,
        moldRemovalTime: 0,
        temperatureControlTime: 0,
        pressureControlTime: 0,
        qualityCheckTime: 0,
        startTime: null,
        endTime: null,
        remark: null
      }
      this.orderDetailList.push(obj)
    },
    /** 工艺路线明细删除按钮操作 */
    handleDeleteOrderDetail() {
      if (this.checkedOrderDetail.length == 0) {
        this.$modal.msgError("请先选择要删除的工艺路线明细数据")
      } else {
        const orderDetailList = this.orderDetailList
        const checkedOrderDetail = this.checkedOrderDetail
        this.orderDetailList = orderDetailList.filter(function(item) {
          return checkedOrderDetail.indexOf(item.index) == -1
        })
      }
    },
    /** 复选框选中数据 */
    handleOrderDetailSelectionChange(selection) {
      this.checkedOrderDetail = selection.map(item => item.index)
    },
    /** 生产订单物料序号 */
    rowOrderMaterialsIndex({ row, rowIndex }) {
      row.index = rowIndex + 1
    },
    /** 生产订单物料添加按钮操作 */
    handleAddOrderMaterials() {
      // 调用多选物料功能
      this.$refs.orderMaterialsMaterialSelect.show()
    },
    /** 生产订单物料删除按钮操作 */
    handleDeleteOrderMaterials() {
      if (this.checkedOrderMaterials.length == 0) {
        this.$modal.msgError("请先选择要删除的生产订单物料数据")
      } else {
        const orderMaterialsList = this.orderMaterialsList
        const checkedOrderMaterials = this.checkedOrderMaterials
        this.orderMaterialsList = orderMaterialsList.filter(function(item) {
          return checkedOrderMaterials.indexOf(item.index) == -1
        })
      }
    },
    /** 复选框选中数据 */
    handleOrderMaterialsSelectionChange(selection) {
      this.checkedOrderMaterials = selection.map(item => item.index)
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('erp/productionOrder/export', {
        ...this.queryParams
      }, `productionOrder_${new Date().getTime()}.xlsx`)
    },
    // 选择产品
    selectProduct() {
      this.currentDetailIndex = -1 // 确保是产品选择模式
      this.$refs.materialSelect.show()
    },
    // 处理物料选择
    handleMaterialSelect(selectedMaterial) {
      if (this.currentDetailIndex >= 0) {
        // 如果是生产订单物料行的物料选择
        this.orderMaterialsList[this.currentDetailIndex].materialId = selectedMaterial.id
        this.orderMaterialsList[this.currentDetailIndex].materialCode = selectedMaterial.materialCode
        this.orderMaterialsList[this.currentDetailIndex].materialName = selectedMaterial.materialName
        this.orderMaterialsList[this.currentDetailIndex].materialSpec = selectedMaterial.materialSpec
        this.orderMaterialsList[this.currentDetailIndex].unitId = selectedMaterial.unitId
        this.orderMaterialsList[this.currentDetailIndex].unitName = selectedMaterial.unitName
        this.currentDetailIndex = -1 // 重置索引
      } else {
        // 如果是产品物料的物料选择
        this.form.productId = selectedMaterial.id
        this.form.productCode = selectedMaterial.materialCode
        this.form.productName = selectedMaterial.materialName
        this.form.productSpec = selectedMaterial.materialSpec
        this.form.unitId = selectedMaterial.unitId
        this.form.unitName = selectedMaterial.unitName

        // 如果订单名称为空，则自动填充物料名称
        if (!this.form.orderName || this.form.orderName.trim() === '') {
          this.form.orderName = selectedMaterial.materialName
        }
      }
    },
    // 清除产品选择
    clearProduct() {
      this.form.productId = null
      this.form.productCode = null
      this.form.productName = null
      this.form.unitId = null
      this.form.unitCode = null
      this.form.unitName = null
    },
    // 选择BOM
    selectBom() {
      this.bomDialogVisible = true
      // 根据产品ID过滤BOM
      const queryParams = {}
      if (this.form.productId) {
        queryParams.productId = this.form.productId
        queryParams.approvalStatus = '2'
      }
      listBom(queryParams).then(response => {
        this.bomList = response.rows
      })
    },
    selectBomRow(row) {
      this.form.bomId = row.id
      this.form.bomCode = row.bomCode
      this.form.bomName = row.bomName
      this.form.processRouteId = row.processRouteId
      this.form.processRouteName = row.processRouteName
      this.bomDialogVisible = false
    },
    // 清除BOM选择
    clearBom() {
      this.form.bomId = null
      this.form.bomCode = null
      this.form.bomName = null
      this.form.processRouteId = null
      this.form.processRouteName = null
    },
    // 选择物料（单选模式，用于编辑现有行）
    selectMaterial(index) {
      this.currentDetailIndex = index
      // 使用单选模式的物料选择组件
      this.$refs.materialSelect.show()
    },
    // 处理生产订单物料选择（多选）
    handleOrderMaterialsMaterialSelect(selectedMaterials) {
      if (Array.isArray(selectedMaterials)) {
        // 多选模式
        selectedMaterials.forEach(material => {
          this.addOrderMaterials(material)
        })
      } else {
        // 单选模式（兼容原有逻辑）
        if (this.currentDetailIndex >= 0) {
          this.orderMaterialsList[this.currentDetailIndex].materialId = selectedMaterials.id
          this.orderMaterialsList[this.currentDetailIndex].materialName = selectedMaterials.materialCode + ' - ' + selectedMaterials.materialName
          this.orderMaterialsList[this.currentDetailIndex].unitId = selectedMaterials.unitId
          this.orderMaterialsList[this.currentDetailIndex].unitName = selectedMaterials.unitName
        }
      }
    },
    // 添加生产订单物料
    addOrderMaterials(material) {
      const newMaterials = {
        id: null,
        orderId: null,
        materialId: material.id,
        materialCode: material.materialCode,
        materialName: material.materialName,
        materialSpec: material.materialSpec,
        quantity: 0,
        issuedQty: 0,
        needQty: 0,
        unitId: material.unitId,
        unitName: material.unitName,
        remark: null
      }
      this.orderMaterialsList.push(newMaterials)
    },
    // 选择工序
    selectProcess(index) {
      this.currentDetailIndex = index
      this.processDialogVisible = true
      listProcess({}).then(response => {
        this.processList = response.rows
      })
    },
    selectProcessRow(row) {
      if (this.currentDetailIndex >= 0) {
        this.orderDetailList[this.currentDetailIndex].processId = row.id
        this.orderDetailList[this.currentDetailIndex].processName = row.processCode + ' - ' + row.processName
      }
      this.processDialogVisible = false
    },
    // 选择工作中心
    selectWorkCenter(index) {
      this.currentDetailIndex = index
      this.workCenterDialogVisible = true
      listWorkCenter({}).then(response => {
        this.workCenterList = response.rows
      })
    },
    selectWorkCenterRow(row) {
      if (this.currentDetailIndex >= 0) {
        this.orderDetailList[this.currentDetailIndex].workCenterId = row.id
        this.orderDetailList[this.currentDetailIndex].workCenterName = row.workCenterCode + ' - ' + row.workCenterName
      }
      this.workCenterDialogVisible = false
    },
    // 选择设备
    selectEquipment(index) {
      this.currentDetailIndex = index
      this.equipmentDialogVisible = true
      listEquipment({}).then(response => {
        this.equipmentList = response.rows
      })
    },
    selectEquipmentRow(row) {
      if (this.currentDetailIndex >= 0) {
        this.orderDetailList[this.currentDetailIndex].equipmentId = row.id
        this.orderDetailList[this.currentDetailIndex].equipmentName = row.equipmentCode + ' - ' + row.equipmentName
      }
      this.equipmentDialogVisible = false
    },
    // 选择模具
    selectMold(index) {
      this.currentDetailIndex = index
      this.moldDialogVisible = true
      listMold({}).then(response => {
        this.moldList = response.rows
      })
    },
    selectMoldRow(row) {
      if (this.currentDetailIndex >= 0) {
        this.orderDetailList[this.currentDetailIndex].moldId = row.id
        this.orderDetailList[this.currentDetailIndex].moldName = row.moldCode + ' - ' + row.moldName
      }
      this.moldDialogVisible = false
    },
    // 选择操作人员
    selectOperator(index) {
      this.currentDetailIndex = index
      this.operatorDialogVisible = true
      listUser({}).then(response => {
        this.operatorList = response.rows
      })
    },
    selectOperatorRow(row) {
      if (this.currentDetailIndex >= 0) {
        this.orderDetailList[this.currentDetailIndex].operatorId = row.userId
        this.orderDetailList[this.currentDetailIndex].operatorName = row.nickName
      }
      this.operatorDialogVisible = false
    },
    // 加载明细
    handleLoadDetails() {
      // 验证必填字段
      if (!this.form.productId) {
        this.$modal.msgError("请先选择产品物料")
        return
      }
      if (!this.form.bomId) {
        this.$modal.msgError("请先选择BOM编号")
        return
      }
      if (!this.form.plannedQuantity || this.form.plannedQuantity <= 0) {
        this.$modal.msgError("请先输入计划数量，且数量应大于0")
        return
      }

      this.loadingDetails = true

      // 并行加载BOM明细和工艺路线明细
      Promise.all([
        this.loadBomMaterials(),
        this.loadProcessRouteDetails()
      ]).then(() => {
        this.$modal.msgSuccess("明细加载成功")
        this.loadingDetails = false
      }).catch(error => {
        console.error("加载明细失败:", error)
        this.$modal.msgError("加载明细失败：" + (error.message || "未知错误"))
        this.loadingDetails = false
      })
    },
    // 加载BOM物料明细
    async loadBomMaterials() {
      try {
        // 获取BOM明细
        const bomDetailResponse = await listBomDetail({ bomId: this.form.bomId })
        const bomDetails = bomDetailResponse.rows || []

        // 清空现有物料明细
        this.orderMaterialsList = []

        // 根据BOM明细和计划数量计算物料需求
        bomDetails.forEach(bomDetail => {
          // 计算物料需求数量：生产订单计划数量 × BOM中该物料的有效用量
          const requiredQuantity = (bomDetail.effectiveQuantity || 0) * this.form.plannedQuantity

          const material = {
            id: null,
            orderId: null,
            materialId: bomDetail.materialId,
            materialCode: bomDetail.materialCode,
            materialName: bomDetail.materialName,
            materialSpec: bomDetail.materialSpec,
            quantity: requiredQuantity, // 根据计划数量和有效用量计算总需求
            issuedQty: 0,
            needQty: requiredQuantity,
            unitId: bomDetail.materialUnitId, // 使用BOM中的组件单位ID
            unitName: bomDetail.materialUnitName, // 使用BOM中的组件单位名称
            remark: bomDetail.remark || null
          }
          this.orderMaterialsList.push(material)
        })
      } catch (error) {
        console.error("加载BOM物料明细失败:", error)
        throw new Error("加载BOM物料明细失败")
      }
    },
    // 加载工艺路线明细
    async loadProcessRouteDetails() {
      try {
        if (!this.form.processRouteId) {
          console.warn("工艺路线ID为空，跳过工艺路线明细加载")
          return
        }

        // 获取工艺路线明细
        const processRouteDetailResponse = await listProcessRouteDetailByRouteId(this.form.processRouteId)
        const processRouteDetails = processRouteDetailResponse.rows || []

        // 清空现有订单明细
        this.orderDetailList = []

                 // 根据工艺路线明细创建工艺路线明细
         processRouteDetails.forEach((routeDetail, index) => {
           // 计算计划工时：生产订单计划数量 × 该工序的周期时间 / 该模具的腔数
           // 注意：周期时间单位是分钟，需要转换为小时
           const cycleTimeMinutes = routeDetail.cycleTime || 0
           const cavityCount = routeDetail.cavityCount || 1 // 如果没有腔数信息，默认为1
           const plannedDurationHours = (this.form.plannedQuantity * cycleTimeMinutes / cavityCount) / 60

           const orderDetail = {
             id: null,
             orderId: null,
             processId: routeDetail.processId,
             processName: routeDetail.processName,
             processOrder: routeDetail.operationSequence || (index + 1),
             workCenterId: routeDetail.workCenterId,
             workCenterName: routeDetail.workCenterName,
             equipmentId: routeDetail.equipmentId,
             equipmentName: routeDetail.equipmentName,
             moldId: routeDetail.moldId,
             moldName: routeDetail.moldName,
             plannedDuration: Math.round(plannedDurationHours * 100) / 100, // 保留2位小数
             actualDuration: 0,
             operatorId: null,
             operatorName: null,
             moldSetupTime: routeDetail.setupTime || 0,
             moldRemovalTime: 0, // 工艺路线中没有拆卸时间，设为0
             temperatureControlTime: 0, // 工艺路线中没有温控时间，设为0
             pressureControlTime: 0, // 工艺路线中没有压力控制时间，设为0
             qualityCheckTime: 0, // 工艺路线中没有质量检查时间，设为0
             startTime: null,
             endTime: null,
             remark: routeDetail.remark || null
           }
           this.orderDetailList.push(orderDetail)
         })
      } catch (error) {
        console.error("加载工艺路线明细失败:", error)
        throw new Error("加载工艺路线明细失败")
      }
    },
    // 清空明细
    handleClearDetails() {
      this.$modal.confirm('确认要清空所有明细信息吗？').then(() => {
        this.orderDetailList = []
        this.orderMaterialsList = []
        this.$modal.msgSuccess("明细已清空")
      }).catch(() => {})
    },
    /** 提交按钮操作 */
    handleSubmit(row) {
      this.$modal.confirm('确认要提交该生产订单吗？').then(() => {
        const data = {
          id: row.id,
          status: '1'
        }
        updateProductionOrderStatus(data).then(response => {
          this.$modal.msgSuccess("提交成功")
          this.getList()
        })
      }).catch(() => {})
    },
    /** 下达按钮操作 */
    handleRelease(row) {
      this.$modal.confirm('确认要下达该生产订单吗？').then(() => {
        const data = {
          id: row.id,
          status: '2'
        }
        updateProductionOrderStatus(data).then(response => {
          this.$modal.msgSuccess("下达成功")
          this.getList()
        })
      }).catch(() => {})
    },
    /** 关闭按钮操作 */
    handleClose(row) {
      this.$modal.confirm('确认要关闭该生产订单吗？').then(() => {
        const data = {
          id: row.id,
          status: '-1'
        }
        updateProductionOrderStatus(data).then(response => {
          this.$modal.msgSuccess("关闭成功")
          this.getList()
        })
      }).catch(() => {})
    },
    /** 获取生产订单条码数据 */
    getProductionOrderBarcodeData(row) {
      const barcodeData = {
        machineCode: row.orderCode,
        machineName: row.orderName,
        machineType: this.getDictLabel(this.dict.type.production_order_type, row.orderType),
        workCenterName: row.productCode ? `${row.productCode} - ${row.productName}` : '未设置'
      };
      console.log('生产订单条码数据:', barcodeData);
      return barcodeData;
    },
    /** 获取字典标签 */
    getDictLabel(dictOptions, value) {
      const item = dictOptions.find(item => item.value === value);
      return item ? item.label : value;
    }
  }
}
</script>
