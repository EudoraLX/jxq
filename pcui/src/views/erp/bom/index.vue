<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="80px">
      <el-form-item label="BOM编码" prop="bomCode">
        <el-input
          v-model="queryParams.bomCode"
          placeholder="请输入BOM编码"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="BOM名称" prop="bomName">
        <el-input
          v-model="queryParams.bomName"
          placeholder="请输入BOM名称"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="版本号" prop="version">
        <el-input
          v-model="queryParams.version"
          placeholder="请输入版本号"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="审批状态" prop="approvalStatus">
        <el-select v-model="queryParams.approvalStatus" placeholder="请选择审批状态" clearable>
          <el-option
            v-for="dict in dict.type.approval_status"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="是否启用" prop="isActive">
        <el-select v-model="queryParams.isActive" placeholder="请选择是否启用" clearable>
          <el-option
            v-for="dict in dict.type.business_active"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
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
          v-hasPermi="['erp:bom:add']"
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
          v-hasPermi="['erp:bom:edit']"
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
          v-hasPermi="['erp:bom:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['erp:bom:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="bomList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="BOM编码" align="center" prop="bomCode" />
      <el-table-column label="BOM名称" align="center" prop="bomName" />
      <el-table-column label="产品编号" align="center" prop="productId" width="200">
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
      <el-table-column label="版本号" align="center" prop="version" />
      <el-table-column label="工艺路线" align="center" prop="processRouteId" width="200">
        <template slot-scope="scope">
          <span v-if="scope.row.processRouteCode && scope.row.processRouteName">
            {{ scope.row.processRouteCode }} - {{ scope.row.processRouteName }}
          </span>
          <span v-else-if="scope.row.processRouteCode">
            {{ scope.row.processRouteCode }}
          </span>
          <span v-else>
            {{ scope.row.processRouteId }}
          </span>
        </template>
      </el-table-column>
      <el-table-column label="生效日期" align="center" prop="effectiveDate" width="100">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.effectiveDate, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="失效日期" align="center" prop="expiryDate" width="100">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.expiryDate, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="审批状态" align="center" prop="approvalStatus">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.approval_status" :value="scope.row.approvalStatus"/>
        </template>
      </el-table-column>
      <el-table-column label="是否默认" align="center" prop="isDefault">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.business_yes_no" :value="scope.row.isDefault"/>
        </template>
      </el-table-column>
      <el-table-column label="总成本" align="center" prop="totalCost" />
      <el-table-column label="是否启用" align="center" prop="isActive">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.business_active" :value="scope.row.isActive"/>
        </template>
      </el-table-column>
      <el-table-column label="条码" align="center" prop="bomCode" width="120">
        <template slot-scope="scope">
          <barcode-display 
            v-if="scope.row.bomCode" 
            :value="scope.row.bomCode" 
            :width="1.5" 
            :height="40"
            format="CODE128"
          />
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width" width="200">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['erp:bom:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['erp:bom:remove']"
          >删除</el-button>
          <!-- 审批状态为0时显示提交按钮 -->
          <el-button
            v-if="scope.row.approvalStatus === '0'"
            size="mini"
            type="text"
            icon="el-icon-upload"
            @click="handleSubmit(scope.row)"
            v-hasPermi="['erp:bom:edit']"
          >提交</el-button>
          <!-- 审批状态为1时显示审批按钮 -->
          <el-button
            v-if="scope.row.approvalStatus === '1'"
            size="mini"
            type="text"
            icon="el-icon-check"
            @click="handleApprove(scope.row)"
            v-hasPermi="['erp:bom:approve']"
          >审批</el-button>
          <!-- 审批状态为2时显示作废按钮 -->
          <el-button
            v-if="scope.row.approvalStatus === '2'"
            size="mini"
            type="text"
            icon="el-icon-close"
            @click="handleCancel(scope.row)"
            v-hasPermi="['erp:bom:approval']"
          >作废</el-button>
          <barcode-printer 
            :machine-data="{
              machineCode: scope.row.bomCode,
              machineName: scope.row.bomName,
              machineType: scope.row.version,
              workCenterName: scope.row.productName || '未设置产品'
            }"
            :title="'BOM标签'"
            :label-title="'BOM标签'"
            :info-labels="{
              code: 'BOM编码',
              name: 'BOM名称',
              type: '版本号',
              workCenter: '产品名称'
            }"
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

    <!-- 添加或修改BOM管理对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="90%" append-to-body>
      <el-tabs v-model="activeTab" type="border-card">
        <!-- 基本信息Tab -->
        <el-tab-pane label="基本信息" name="basic">
          <el-form ref="form" :model="form" :rules="rules" label-width="120px" style="padding: 20px;">
        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item label="BOM编码" prop="bomCode">
              <el-input v-model="form.bomCode" placeholder="BOM编码" readonly prefix-icon="el-icon-document" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="BOM名称" prop="bomName">
              <el-input v-model="form.bomName" placeholder="请输入BOM名称" prefix-icon="el-icon-edit" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item label="产品编号" prop="productId">
              <div style="display: flex; align-items: center;">
                <div style="flex: 1; position: relative;" @click="selectProduct">
                  <el-input
                    v-model="form.productCode"
                    placeholder="请选择产品编号"
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
          <el-col :span="12">
            <el-form-item label="单位" prop="unitId">
              <el-input v-model="form.unitName" placeholder="单位" readonly prefix-icon="el-icon-scale" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item label="版本号" prop="version">
              <el-input v-model="form.version" placeholder="版本号" readonly prefix-icon="el-icon-collection" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="工艺路线" prop="processRouteId">
              <div style="display: flex; align-items: center;">
                <div style="flex: 1; position: relative;" @click="selectProcessRoute">
                  <el-input
                    v-model="form.processRouteName"
                    placeholder="请选择工艺路线"
                    readonly
                    style="background-color: #f5f7fa; cursor: pointer;"
                    prefix-icon="el-icon-s-operation"
                  />
                </div>
                <el-button
                  type="primary"
                  icon="el-icon-arrow-down"
                  size="mini"
                  @click="selectProcessRoute"
                  style="margin-left: 10px;"
                >
                  选择
                </el-button>
                <el-button
                  v-if="form.processRouteId"
                  type="danger"
                  icon="el-icon-delete"
                  size="mini"
                  @click="clearProcessRoute"
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
            <el-form-item label="生效日期" prop="effectiveDate">
              <el-date-picker 
                clearable
                v-model="form.effectiveDate"
                type="date"
                value-format="yyyy-MM-dd"
                placeholder="请选择生效日期"
                style="width: 100%;"
                prefix-icon="el-icon-calendar"
              />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="失效日期" prop="expiryDate">
              <el-date-picker 
                clearable
                v-model="form.expiryDate"
                type="date"
                value-format="yyyy-MM-dd"
                placeholder="请选择失效日期"
                style="width: 100%;"
                prefix-icon="el-icon-calendar"
              />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item label="审批状态" prop="approvalStatus">
              <el-input v-model="form.approvalStatusName" placeholder="审批状态" readonly prefix-icon="el-icon-check" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="是否默认" prop="isDefault">
              <el-radio-group v-model="form.isDefault">
                <el-radio
                  v-for="dict in dict.type.business_yes_no"
                  :key="dict.value"
                  :label="dict.value"
                >{{dict.label}}</el-radio>
              </el-radio-group>
            </el-form-item>
          </el-col>
        </el-row>
            <el-row :gutter="20">
              <el-col :span="12">
                <el-form-item label="是否启用" prop="isActive">
                  <el-radio-group v-model="form.isActive">
                    <el-radio
                      v-for="dict in dict.type.business_active"
                      :key="dict.value"
                      :label="dict.value"
                    >{{dict.label}}</el-radio>
                  </el-radio-group>
                </el-form-item>
              </el-col>
              <el-col :span="12">
                <el-form-item label="备注" prop="remark">
                  <el-input v-model="form.remark" type="textarea" placeholder="请输入备注信息" />
                </el-form-item>
              </el-col>
            </el-row>
          </el-form>
        </el-tab-pane>

        <!-- 成本信息Tab -->
        <el-tab-pane label="成本信息" name="cost">
          <el-form ref="form" :model="form" label-width="120px" style="padding: 20px;">
            <el-row :gutter="20">
              <el-col :span="12">
                <el-form-item label="总成本" prop="totalCost">
                  <el-input-number 
                    v-model="form.totalCost" 
                    :precision="2" 
                    :step="0.01" 
                    placeholder="请输入总成本" 
                    style="width: 100%;"
                    :min="0"
                  />
                </el-form-item>
              </el-col>
              <el-col :span="12">
                <el-form-item label="人工成本" prop="laborCost">
                  <el-input-number 
                    v-model="form.laborCost" 
                    :precision="2" 
                    :step="0.01" 
                    placeholder="请输入人工成本" 
                    style="width: 100%;"
                    :min="0"
                  />
                </el-form-item>
              </el-col>
            </el-row>

            <el-row :gutter="20">
              <el-col :span="12">
                <el-form-item label="物料成本" prop="materialCost">
                  <el-input-number 
                    v-model="form.materialCost" 
                    :precision="2" 
                    :step="0.01" 
                    placeholder="请输入物料成本" 
                    style="width: 100%;"
                    :min="0"
                  />
                </el-form-item>
              </el-col>
              <el-col :span="12">
                <el-form-item label="制造费用" prop="overheadCost">
                  <el-input-number 
                    v-model="form.overheadCost" 
                    :precision="2" 
                    :step="0.01" 
                    placeholder="请输入制造费用" 
                    style="width: 100%;"
                    :min="0"
                  />
                </el-form-item>
              </el-col>
            </el-row>
          </el-form>
        </el-tab-pane>

        <!-- BOM明细信息Tab -->
        <el-tab-pane label="BOM明细信息" name="bomDetail">
            <el-row :gutter="10" class="mb8">
              <el-col :span="1.5">
                <el-button type="primary" icon="el-icon-plus" size="mini" @click="handleAddBomDetail">添加</el-button>
              </el-col>
              <el-col :span="1.5">
                <el-button type="danger" icon="el-icon-delete" size="mini" @click="handleDeleteBomDetail">删除</el-button>
              </el-col>
            </el-row>
            <el-table :data="bomDetailList" :row-class-name="rowBomDetailIndex" @selection-change="handleBomDetailSelectionChange" ref="bomDetail">
              <el-table-column type="selection" width="50" align="center" />
              <el-table-column label="序号" align="center" prop="index" width="50"/>
              <el-table-column label="组件物料" prop="materialId" width="200">
                <template slot-scope="scope">
                  <el-input v-model="scope.row.materialName" placeholder="请选择组件物料" readonly @click="selectMaterial(scope.$index)">
                    <el-button slot="append" icon="el-icon-search" @click="selectMaterial(scope.$index)"></el-button>
                  </el-input>
                </template>
              </el-table-column>
              <el-table-column label="组件单位" prop="materialUnitId" width="120">
                <template slot-scope="scope">
                  <el-input v-model="scope.row.materialUnitName" placeholder="组件单位" readonly />
                </template>
              </el-table-column>
              <el-table-column label="单件用量" prop="quantityPerUnit" width="120">
                <template slot-scope="scope">
                  <el-input 
                    v-model="scope.row.quantityPerUnit" 
                    placeholder="请输入单件用量" 
                    @input="calculateEffectiveQuantity(scope.$index)"
                    type="number"
                    :min="0"
                    :step="0.01"
                  />
                </template>
              </el-table-column>
              <el-table-column label="损耗率" prop="lossRate" width="120">
                <template slot-scope="scope">
                  <el-input 
                    v-model="scope.row.lossRate" 
                    placeholder="请输入损耗率" 
                    @input="calculateEffectiveQuantity(scope.$index)"
                    type="number"
                    :min="0"
                    :max="100"
                    :step="0.01"
                  />
                </template>
              </el-table-column>
              <el-table-column label="有效用量" prop="effectiveQuantity" width="120">
                <template slot-scope="scope">
                  <el-input v-model="scope.row.effectiveQuantity" placeholder="有效用量" readonly />
                </template>
              </el-table-column>
              <el-table-column label="位置" prop="position" width="120">
                <template slot-scope="scope">
                  <el-input v-model="scope.row.position" placeholder="请输入位置" />
                </template>
              </el-table-column>
              <el-table-column label="工序顺序" prop="operationSequence" width="120">
                <template slot-scope="scope">
                  <el-input v-model="scope.row.operationSequence" placeholder="请输入工序顺序" />
                </template>
              </el-table-column>
              <el-table-column label="是否关键件" prop="isCritical" width="120">
                <template slot-scope="scope">
                  <el-select v-model="scope.row.isCritical" placeholder="请选择是否关键件">
                    <el-option
                      v-for="dict in dict.type.business_yes_no"
                      :key="dict.value"
                      :label="dict.label"
                      :value="dict.value"
                    ></el-option>
                  </el-select>
                </template>
              </el-table-column>
              <el-table-column label="是否可选件" prop="isOptional" width="120">
                <template slot-scope="scope">
                  <el-select v-model="scope.row.isOptional" placeholder="请选择是否可选件">
                    <el-option
                      v-for="dict in dict.type.business_yes_no"
                      :key="dict.value"
                      :label="dict.label"
                      :value="dict.value"
                    ></el-option>
                  </el-select>
                </template>
              </el-table-column>
              <el-table-column label="单位成本" prop="unitCost" width="120">
                <template slot-scope="scope">
                  <el-input v-model="scope.row.unitCost" placeholder="单位成本" readonly />
                </template>
              </el-table-column>
              <el-table-column label="总成本" prop="totalCost" width="120">
                <template slot-scope="scope">
                  <el-input v-model="scope.row.totalCost" placeholder="总成本" readonly />
                </template>
              </el-table-column>
            </el-table>
          </el-tab-pane>
          
        <!-- 工艺路线明细信息Tab -->
        <el-tab-pane label="工艺路线明细信息" name="routeDetail">
            <el-row :gutter="10" class="mb8">
              <el-col :span="1.5">
                <el-button type="primary" icon="el-icon-plus" size="mini" @click="handleAddBomRouteDetail">添加</el-button>
              </el-col>
              <el-col :span="1.5">
                <el-button type="danger" icon="el-icon-delete" size="mini" @click="handleDeleteBomRouteDetail">删除</el-button>
              </el-col>
            </el-row>
            <el-table :data="bomRouteDetailList" :row-class-name="rowBomRouteDetailIndex" @selection-change="handleBomRouteDetailSelectionChange" ref="bomRouteDetail">
          <el-table-column type="selection" width="50" align="center" />
          <el-table-column label="序号" align="center" prop="index" width="50"/>
          <el-table-column label="工序" prop="processId" width="150">
            <template slot-scope="scope">
              <el-input v-model="scope.row.processName" placeholder="请选择工序" readonly @click="selectProcess(scope.$index)">
                <el-button slot="append" icon="el-icon-search" @click="selectProcess(scope.$index)"></el-button>
              </el-input>
            </template>
          </el-table-column>
          <el-table-column label="工序顺序" prop="operationSequence" width="120">
            <template slot-scope="scope">
              <el-input v-model="scope.row.operationSequence" placeholder="请输入工序顺序" />
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
              <el-input v-model="scope.row.equipmentName" placeholder="根据工作中心自动填充" readonly>
              </el-input>
            </template>
          </el-table-column>
          <el-table-column label="模具" prop="moldId" width="150">
            <template slot-scope="scope">
              <el-input v-model="scope.row.moldName" placeholder="根据工作中心自动填充" readonly>
              </el-input>
            </template>
          </el-table-column>
          <el-table-column label="准备时间" prop="setupTime" width="120">
            <template slot-scope="scope">
              <el-input v-model="scope.row.setupTime" placeholder="请输入准备时间" />
            </template>
          </el-table-column>
          <el-table-column label="加工时间" prop="processingTime" width="120">
            <template slot-scope="scope">
              <el-input v-model="scope.row.processingTime" placeholder="请输入加工时间" />
            </template>
          </el-table-column>
          <el-table-column label="移动时间" prop="moveTime" width="120">
            <template slot-scope="scope">
              <el-input v-model="scope.row.moveTime" placeholder="请输入移动时间" />
            </template>
          </el-table-column>
          <el-table-column label="等待时间" prop="queueTime" width="120">
            <template slot-scope="scope">
              <el-input v-model="scope.row.queueTime" placeholder="请输入等待时间" />
            </template>
          </el-table-column>
          <el-table-column label="周期时间" prop="cycleTime" width="120">
            <template slot-scope="scope">
              <el-input v-model="scope.row.cycleTime" placeholder="周期时间" readonly />
            </template>
          </el-table-column>
          <el-table-column label="人工工时" prop="laborHours" width="120">
            <template slot-scope="scope">
              <el-input v-model="scope.row.laborHours" placeholder="请输入人工工时" />
            </template>
          </el-table-column>
          <el-table-column label="机器工时" prop="machineHours" width="120">
            <template slot-scope="scope">
              <el-input v-model="scope.row.machineHours" placeholder="请输入机器工时" />
            </template>
          </el-table-column>
          <el-table-column label="是否关键工序" prop="isCritical" width="120">
            <template slot-scope="scope">
              <el-select v-model="scope.row.isCritical" placeholder="请选择是否关键工序">
                <el-option
                  v-for="dict in dict.type.business_yes_no"
                  :key="dict.value"
                  :label="dict.label"
                  :value="dict.value"
                ></el-option>
              </el-select>
            </template>
          </el-table-column>
          <el-table-column label="是否可选工序" prop="isOptional" width="120">
            <template slot-scope="scope">
              <el-select v-model="scope.row.isOptional" placeholder="请选择是否可选工序">
                <el-option
                  v-for="dict in dict.type.business_yes_no"
                  :key="dict.value"
                  :label="dict.label"
                  :value="dict.value"
                ></el-option>
              </el-select>
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
    <!-- BOM明细物料选择组件（多选模式） -->
    <material-select ref="bomDetailMaterialSelect" :only-active="true" :multiple="true" @select="handleBomDetailMaterialSelect" />

    <!-- 工艺路线选择对话框 -->
    <el-dialog title="选择工艺路线" :visible.sync="processRouteDialogVisible" width="800px" append-to-body>
      <el-table :data="processRouteList" @row-click="selectProcessRouteRow">
        <el-table-column label="工艺路线编码" prop="processRouteCode" />
        <el-table-column label="工艺路线名称" prop="processRouteName" />
        <el-table-column label="版本号" prop="version" />
      </el-table>
      <div slot="footer" class="dialog-footer">
        <el-button @click="processRouteDialogVisible = false">取 消</el-button>
      </div>
    </el-dialog>

    <!-- 物料选择对话框 -->
    <el-dialog title="选择物料" :visible.sync="materialDialogVisible" width="800px" append-to-body>
      <el-table :data="materialList" @row-click="selectMaterialRow">
        <el-table-column label="物料编码" prop="materialCode" />
        <el-table-column label="物料名称" prop="materialName" />
        <el-table-column label="规格型号" prop="materialSpec" />
        <el-table-column label="单位" prop="unitName" />
      </el-table>
      <div slot="footer" class="dialog-footer">
        <el-button @click="materialDialogVisible = false">取 消</el-button>
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
  </div>
</template>

<script>
import { listBom, getBom, delBom, addBom, updateBom, checkBomConflict, approveBom } from "@/api/erp/bom"
import { listMasterMaterial } from "@/api/erp/masterMaterial"
import { listProcessRoute } from "@/api/erp/processRoute"
import { listProcessRouteDetailByRouteId } from "@/api/erp/processRouteDetail"
import { listProcess } from "@/api/erp/process"
import { listWorkCenter } from "@/api/erp/workCenter"
import { listEquipment } from "@/api/erp/equipment"
import { listMold } from "@/api/erp/mold"
import request from '@/utils/request'
import MaterialSelect from "@/components/MaterialSelect"
import BarcodeDisplay from "@/components/BarcodeDisplay"
import BarcodePrinter from "@/components/BarcodePrinter"

export default {
  name: "Bom",
  components: {
    MaterialSelect,
    BarcodeDisplay,
    BarcodePrinter
  },
  dicts: ['approval_status', 'business_yes_no', 'business_active'],
  data() {
    return {
      // 遮罩层
      loading: true,
      // 选中数组
      ids: [],
      // 子表选中数据
      checkedBomDetail: [],
      checkedBomRouteDetail: [],
      // 非单个禁用
      single: true,
      // 非多个禁用
      multiple: true,
      // 显示搜索条件
      showSearch: true,
      // 总条数
      total: 0,
      // BOM管理表格数据
      bomList: [],
      // BOM明细表格数据
      bomDetailList: [],
      // 工艺路线明细表格数据
      bomRouteDetailList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 当前激活的Tab
      activeTab: 'basic',
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        bomCode: null,
        bomName: null,
        version: null,
        approvalStatus: null,
        isActive: null
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        bomCode: [
          { required: true, message: "BOM编码不能为空", trigger: "blur" }
        ],
        bomName: [
          { required: true, message: "BOM名称不能为空", trigger: "blur" }
        ],
        productId: [
          { required: true, message: "产品编号不能为空", trigger: "change" }
        ],
        version: [
          { required: true, message: "版本号不能为空", trigger: "blur" }
        ],
        effectiveDate: [
          { required: true, message: "生效日期不能为空", trigger: "blur" }
        ],
        approvalStatus: [
          { required: true, message: "审批状态不能为空", trigger: "change" }
        ],
        isActive: [
          { required: true, message: "是否启用不能为空", trigger: "change" }
        ]
      },
      // 对话框显示状态
      processRouteDialogVisible: false,
      materialDialogVisible: false,
      processDialogVisible: false,
      workCenterDialogVisible: false,
      equipmentDialogVisible: false,
      moldDialogVisible: false,
      // 选择数据
      processRouteList: [],
      materialList: [],
      processList: [],
      workCenterList: [],
      equipmentList: [],
      moldList: [],
      // 当前选择的行索引
      currentDetailIndex: -1,
      currentRouteDetailIndex: -1
    }
  },
  created() {
    this.getList()
  },
  methods: {
    /** 查询BOM管理列表 */
    getList() {
      this.loading = true
      listBom(this.queryParams).then(response => {
        this.bomList = response.rows
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
        bomCode: null,
        bomName: null,
        productId: null,
        unitId: null,
        version: "1.0",
        processRouteId: null,
        effectiveDate: null,
        expiryDate: null,
        approvalStatus: "0",
        approvalStatusName: "待审批",
        isDefault: "0",
        totalCost: 0,
        laborCost: 0,
        materialCost: 0,
        overheadCost: 0,
        isActive: "1",
        remark: null
      }
      this.bomDetailList = []
      this.bomRouteDetailList = []
      this.resetForm("form")
    },
    /** 搜索按钮操作 */
    handleQuery() {
      this.queryParams.pageNum = 1
      this.getList()
    },
    /** 重置按钮操作 */
    resetQuery() {
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
      this.title = "添加BOM管理"
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset()
      const id = row.id || this.ids
      getBom(id).then(response => {
        this.form = response.data
        // 设置审批状态名称
        const approvalStatusDict = this.dict.type.approval_status.find(item => item.value === this.form.approvalStatus)
        this.form.approvalStatusName = approvalStatusDict ? approvalStatusDict.label : '待审批'
        this.bomDetailList = response.data.erpBomDetailList || []
        this.bomRouteDetailList = response.data.erpBomRouteDetailList || []
        this.open = true
        this.title = "修改BOM管理"
      })
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          this.form.erpBomDetailList = this.bomDetailList
          this.form.erpBomRouteDetailList = this.bomRouteDetailList
          
          // 为工艺路线明细设置bomId（新增时会在后端设置，修改时保持现有ID）
          if (this.form.erpBomRouteDetailList && this.form.erpBomRouteDetailList.length > 0) {
            this.form.erpBomRouteDetailList.forEach(detail => {
              if (this.form.id != null) {
                // 修改时，保持现有的bomId
                detail.bomId = this.form.id
              }
              // 新增时，bomId会在后端自动设置
            })
          }
          
          if (this.form.id != null) {
            updateBom(this.form).then(response => {
              this.$modal.msgSuccess("修改成功")
              this.open = false
              this.getList()
            })
          } else {
            addBom(this.form).then(response => {
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
      this.$modal.confirm('是否确认删除BOM管理编号为"' + ids + '"的数据项？').then(function() {
        return delBom(ids)
      }).then(() => {
        this.getList()
        this.$modal.msgSuccess("删除成功")
      }).catch(() => {})
    },
    /** BOM明细序号 */
    rowBomDetailIndex({ row, rowIndex }) {
      row.index = rowIndex + 1
    },
    /** BOM明细添加按钮操作 */
    handleAddBomDetail() {
      // 调用多选物料功能
      this.$refs.bomDetailMaterialSelect.show()
    },
    /** BOM明细删除按钮操作 */
    handleDeleteBomDetail() {
      if (this.checkedBomDetail.length == 0) {
        this.$modal.msgError("请先选择要删除的BOM明细数据")
      } else {
        const bomDetailList = this.bomDetailList
        const checkedBomDetail = this.checkedBomDetail
        this.bomDetailList = bomDetailList.filter(function(item) {
          return checkedBomDetail.indexOf(item.index) == -1
        })
      }
    },
    /** 复选框选中数据 */
    handleBomDetailSelectionChange(selection) {
      this.checkedBomDetail = selection.map(item => item.index)
    },
    /** 工艺路线明细序号 */
    rowBomRouteDetailIndex({ row, rowIndex }) {
      row.index = rowIndex + 1
    },
    /** 工艺路线明细添加按钮操作 */
    handleAddBomRouteDetail() {
      let obj = {
        processId: null,
        operationSequence: 0,
        workCenterId: null,
        equipmentId: null,
        moldId: null,
        setupTime: 0,
        processingTime: 0,
        moveTime: 0,
        queueTime: 0,
        cycleTime: 0,
        laborHours: 0,
        machineHours: 0,
        isCritical: "0",
        isOptional: "0",
        remark: null
      }
      this.bomRouteDetailList.push(obj)
    },
    /** 工艺路线明细删除按钮操作 */
    handleDeleteBomRouteDetail() {
      if (this.checkedBomRouteDetail.length == 0) {
        this.$modal.msgError("请先选择要删除的工艺路线明细数据")
      } else {
        const bomRouteDetailList = this.bomRouteDetailList
        const checkedBomRouteDetail = this.checkedBomRouteDetail
        this.bomRouteDetailList = bomRouteDetailList.filter(function(item) {
          return checkedBomRouteDetail.indexOf(item.index) == -1
        })
      }
    },
    /** 复选框选中数据 */
    handleBomRouteDetailSelectionChange(selection) {
      this.checkedBomRouteDetail = selection.map(item => item.index)
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('erp/bom/export', {
        ...this.queryParams
      }, `bom_${new Date().getTime()}.xlsx`)
    },
    // 选择产品
    selectProduct() {
      this.currentDetailIndex = -1 // 确保是产品选择模式
      this.$refs.materialSelect.show()
    },
    // 处理物料选择
    handleMaterialSelect(selectedMaterial) {
      if (this.currentDetailIndex >= 0) {
        // 如果是BOM明细行的物料选择
        this.bomDetailList[this.currentDetailIndex].materialId = selectedMaterial.id
        this.bomDetailList[this.currentDetailIndex].materialCode = selectedMaterial.materialCode
        this.bomDetailList[this.currentDetailIndex].materialName = selectedMaterial.materialName
        this.bomDetailList[this.currentDetailIndex].materialSpec = selectedMaterial.materialSpec
        this.bomDetailList[this.currentDetailIndex].materialUnitId = selectedMaterial.unitId // 修正字段名
        this.bomDetailList[this.currentDetailIndex].materialUnitName = selectedMaterial.unitName // 修正字段名
        this.currentDetailIndex = -1 // 重置索引
      } else {
        // 如果是产品编号的物料选择
        this.form.productId = selectedMaterial.id
        this.form.productCode = selectedMaterial.materialCode
        this.form.productName = selectedMaterial.materialName
        this.form.productSpec = selectedMaterial.materialSpec
        this.form.unitId = selectedMaterial.unitId
        this.form.unitName = selectedMaterial.unitName
        
        // 如果BOM名称为空，则自动填充物料名称
        if (!this.form.bomName || this.form.bomName.trim() === '') {
          this.form.bomName = selectedMaterial.materialName
        }
      }
    },
    // 清除产品选择
    clearProduct() {
      this.form.productId = null
      this.form.productCode = null
      this.form.productName = null
      this.form.productSpec = null
      this.form.unitId = null
      this.form.unitName = null
    },
    // 选择工艺路线
    selectProcessRoute() {
      this.processRouteDialogVisible = true
      listProcessRoute({}).then(response => {
        this.processRouteList = response.rows
      })
    },
    selectProcessRouteRow(row) {
      this.form.processRouteId = row.id
      this.form.processRouteName = row.processRouteCode + ' - ' + row.processRouteName
      this.processRouteDialogVisible = false
      // 加载工艺路线明细数据
      this.loadProcessRouteDetails(row.id)
    },
    // 清除工艺路线选择
    clearProcessRoute() {
      this.form.processRouteId = null
      this.form.processRouteCode = null
      this.form.processRouteName = null
      // 清空工艺路线明细数据
      this.bomRouteDetailList = []
    },
    // 加载工艺路线明细数据
    loadProcessRouteDetails(processRouteId) {
      if (!processRouteId) {
        this.bomRouteDetailList = []
        return
      }
      
      console.log('开始加载工艺路线明细，processRouteId:', processRouteId)
      
      // 调用工艺路线明细API
      listProcessRouteDetailByRouteId(processRouteId).then(response => {
        console.log('工艺路线明细API响应:', response)
        console.log('响应数据结构:', {
          hasData: !!response.data,
          hasRows: !!response.rows,
          dataRows: response.data?.rows,
          directRows: response.rows
        })
        
        let detailList = []
        if (response.data && response.data.rows) {
          detailList = response.data.rows
        } else if (response.rows) {
          detailList = response.rows
        }
        
        if (detailList && detailList.length > 0) {
          console.log('开始转换工艺路线明细数据，数量:', detailList.length)
          // 将工艺路线明细转换为BOM工艺路线明细格式
          this.bomRouteDetailList = detailList.map(detail => ({
            id: null, // 新数据，没有ID
            bomId: null, // 保存时会设置
            processRouteDetailId: detail.id, // 关联的工艺路线明细ID
            operationSequence: detail.operationSequence,
            processId: detail.processId,
            processCode: detail.processCode,
            processName: detail.processName,
            workCenterId: detail.workCenterId,
            workCenterCode: detail.workCenterCode,
            workCenterName: detail.workCenterName,
            equipmentId: detail.equipmentId,
            equipmentCode: detail.equipmentCode,
            equipmentName: detail.equipmentName,
            moldId: detail.moldId,
            moldCode: detail.moldCode,
            moldName: detail.moldName,
            setupTime: detail.setupTime || 0,
            processingTime: detail.processingTime || 0,
            moveTime: detail.moveTime || 0,
            queueTime: detail.queueTime || 0,
            cycleTime: detail.cycleTime || 0,
            laborHours: detail.laborHours || 0,
            machineHours: detail.machineHours || 0,
            isCritical: detail.isCritical || '0',
            isOptional: detail.isOptional || '0',
            remark: detail.remark
          }))
          console.log('转换后的BOM工艺路线明细数据:', this.bomRouteDetailList)
        } else {
          console.log('没有找到工艺路线明细数据')
          this.bomRouteDetailList = []
        }
      }).catch(error => {
        console.error('加载工艺路线明细失败:', error)
        this.bomRouteDetailList = []
        this.$modal.msgError('加载工艺路线明细失败')
      })
    },
    // 选择物料（单选模式，用于编辑现有行）
    selectMaterial(index) {
      this.currentDetailIndex = index
      // 使用单选模式的物料选择组件
      this.$refs.materialSelect.show()
    },
    selectMaterialRow(row) {
      if (this.currentDetailIndex >= 0) {
        this.bomDetailList[this.currentDetailIndex].materialId = row.id
        this.bomDetailList[this.currentDetailIndex].materialName = row.materialCode + ' - ' + row.materialName
        this.bomDetailList[this.currentDetailIndex].materialUnitId = row.unitId
        this.bomDetailList[this.currentDetailIndex].materialUnitName = row.unitName
      }
      this.materialDialogVisible = false
    },
    // 处理BOM明细物料选择（多选）
    handleBomDetailMaterialSelect(selectedMaterials) {
      if (Array.isArray(selectedMaterials)) {
        // 多选模式
        selectedMaterials.forEach(material => {
          this.addBomDetail(material)
        })
      } else {
        // 单选模式（兼容原有逻辑）
        if (this.currentDetailIndex >= 0) {
          this.bomDetailList[this.currentDetailIndex].materialId = selectedMaterials.id
          this.bomDetailList[this.currentDetailIndex].materialName = selectedMaterials.materialCode + ' - ' + selectedMaterials.materialName
          this.bomDetailList[this.currentDetailIndex].materialUnitId = selectedMaterials.unitId
          this.bomDetailList[this.currentDetailIndex].materialUnitName = selectedMaterials.unitName
        }
      }
    },
    // 添加BOM明细
    addBomDetail(material) {
      const newDetail = {
        id: null,
        bomId: null,
        materialId: material.id,
        materialCode: material.materialCode,
        materialName: material.materialName,
        materialSpec: material.materialSpec,
        materialUnitId: material.unitId, // 修正字段名
        materialUnitName: material.unitName, // 修正字段名
        quantityPerUnit: 1,
        lossRate: 0, // 默认损耗率为0
        effectiveQuantity: 1, // 默认有效用量为1
        isCritical: '1', // 默认为关键件
        remark: null
      }
      this.bomDetailList.push(newDetail)
    },
    // 计算有效用量
    calculateEffectiveQuantity(index) {
      if (index >= 0 && index < this.bomDetailList.length) {
        const detail = this.bomDetailList[index]
        const quantityPerUnit = parseFloat(detail.quantityPerUnit) || 0
        const lossRate = parseFloat(detail.lossRate) || 0
        
        // 有效用量 = 单位用量 × (1 - 损耗率%)
        const effectiveQuantity = quantityPerUnit * (1 - lossRate / 100)
        
        // 保留2位小数
        this.bomDetailList[index].effectiveQuantity = Math.round(effectiveQuantity * 100) / 100
      }
    },
    // 选择工序
    selectProcess(index) {
      this.currentRouteDetailIndex = index
      this.processDialogVisible = true
      listProcess({}).then(response => {
        this.processList = response.rows
      })
    },
    selectProcessRow(row) {
      if (this.currentRouteDetailIndex >= 0) {
        this.bomRouteDetailList[this.currentRouteDetailIndex].processId = row.id
        this.bomRouteDetailList[this.currentRouteDetailIndex].processName = row.processCode + ' - ' + row.processName
      }
      this.processDialogVisible = false
    },
    // 选择工作中心
    selectWorkCenter(index) {
      this.currentRouteDetailIndex = index
      this.workCenterDialogVisible = true
      listWorkCenter({}).then(response => {
        this.workCenterList = response.rows
      })
    },
    selectWorkCenterRow(row) {
      if (this.currentRouteDetailIndex >= 0) {
        this.bomRouteDetailList[this.currentRouteDetailIndex].workCenterId = row.id
        this.bomRouteDetailList[this.currentRouteDetailIndex].workCenterName = row.workCenterCode + ' - ' + row.workCenterName
        
        // 根据工作中心自动更新设备和模具
        this.updateEquipmentAndMoldByWorkCenter(this.currentRouteDetailIndex, row.id)
      }
      this.workCenterDialogVisible = false
    },
    // 根据工作中心更新设备和模具
    updateEquipmentAndMoldByWorkCenter(index, workCenterId) {
      // 根据工作中心ID查询首选配置的设备和模具
      this.getWorkCenterEquipmentAndMold(workCenterId).then(response => {
        if (response.data) {
          const data = response.data
          
          // 更新设备信息（首选配置）
          if (data.equipment_id) {
            this.bomRouteDetailList[index].equipmentId = data.equipment_id
            this.bomRouteDetailList[index].equipmentName = data.equipment_code + ' - ' + data.equipment_name
          } else {
            this.bomRouteDetailList[index].equipmentId = null
            this.bomRouteDetailList[index].equipmentName = ''
          }
          
          // 更新模具信息（首选配置）
          if (data.mold_id) {
            this.bomRouteDetailList[index].moldId = data.mold_id
            this.bomRouteDetailList[index].moldName = data.mold_code + ' - ' + data.mold_name
          } else {
            this.bomRouteDetailList[index].moldId = null
            this.bomRouteDetailList[index].moldName = ''
          }
        } else {
          // 没有找到首选配置，清空设备和模具信息
          this.bomRouteDetailList[index].equipmentId = null
          this.bomRouteDetailList[index].equipmentName = ''
          this.bomRouteDetailList[index].moldId = null
          this.bomRouteDetailList[index].moldName = ''
        }
      }).catch(error => {
        console.error('获取工作中心首选配置的设备和模具失败:', error)
        // 清空设备和模具信息
        this.bomRouteDetailList[index].equipmentId = null
        this.bomRouteDetailList[index].equipmentName = ''
        this.bomRouteDetailList[index].moldId = null
        this.bomRouteDetailList[index].moldName = ''
      })
    },
    // 获取工作中心关联的设备和模具
    getWorkCenterEquipmentAndMold(workCenterId) {
      return request({
        url: '/erp/workCenter/' + workCenterId + '/equipmentAndMold',
        method: 'get'
      })
    },
    // 选择设备
    selectEquipment(index) {
      this.currentRouteDetailIndex = index
      this.equipmentDialogVisible = true
      listEquipment({}).then(response => {
        this.equipmentList = response.rows
      })
    },
    selectEquipmentRow(row) {
      if (this.currentRouteDetailIndex >= 0) {
        this.bomRouteDetailList[this.currentRouteDetailIndex].equipmentId = row.id
        this.bomRouteDetailList[this.currentRouteDetailIndex].equipmentName = row.equipmentCode + ' - ' + row.equipmentName
      }
      this.equipmentDialogVisible = false
    },
    // 选择模具
    selectMold(index) {
      this.currentRouteDetailIndex = index
      this.moldDialogVisible = true
      listMold({}).then(response => {
        this.moldList = response.rows
      })
    },
    selectMoldRow(row) {
      if (this.currentRouteDetailIndex >= 0) {
        this.bomRouteDetailList[this.currentRouteDetailIndex].moldId = row.id
        this.bomRouteDetailList[this.currentRouteDetailIndex].moldName = row.moldCode + ' - ' + row.moldName
      }
      this.moldDialogVisible = false
    },
    /** 提交BOM审批 */
    handleSubmit(row) {
      this.$modal.confirm('是否确认提交该BOM进行审批？').then(() => {
        const data = {
          id: row.id,
          approvalStatus: '1'
        }
        updateBom(data).then(response => {
          this.$modal.msgSuccess("提交成功")
          this.getList()
        })
      }).catch(() => {})
    },
    /** 审批BOM */
    handleApprove(row) {
      this.$modal.confirm('是否确认审批该BOM？').then(() => {
        this.checkBomConflictBeforeApprove(row)
      }).catch(() => {})
    },
    /** 审批前检查冲突 */
    checkBomConflictBeforeApprove(row) {
      if (!row.productId || !row.effectiveDate) {
        this.$modal.msgError("BOM信息不完整，无法进行审批")
        return
      }
      
      // 格式化日期为 yyyy-MM-dd 格式
      const effectiveDate = this.formatDate(row.effectiveDate)
      
      checkBomConflict(row.productId, effectiveDate).then(response => {
        const conflicts = response.data
        if (conflicts && conflicts.length > 0) {
          this.showConflictConfirmDialog(row, conflicts)
        } else {
          this.performApprove(row, false)
        }
      }).catch(error => {
        this.$modal.msgError("检查冲突失败：" + error.message)
      })
    },
    /** 显示冲突确认对话框 */
    showConflictConfirmDialog(row, conflicts) {
      const conflictInfo = conflicts.map(conflict => 
        `BOM编码：${conflict.bomCode}，BOM名称：${conflict.bomName}`
      ).join('\n')
      
      this.$modal.confirm(
        `检测到以下BOM存在冲突：\n${conflictInfo}\n\n是否继续审批？继续审批将自动更新冲突BOM的失效日期。`,
        'BOM冲突确认',
        {
          confirmButtonText: '继续审批',
          cancelButtonText: '取消',
          type: 'warning'
        }
      ).then(() => {
        this.performApprove(row, true)
      }).catch(() => {})
    },
    /** 执行审批 */
    performApprove(row, forceApprove) {
      const data = {
        bomId: row.id,
        forceApprove: forceApprove
      }
      
      approveBom(data).then(response => {
        this.$modal.msgSuccess("审批成功")
        this.getList()
      }).catch(error => {
        this.$modal.msgError("审批失败：" + error.message)
      })
    },
    /** 格式化日期 */
    formatDate(date) {
      if (!date) return ''
      const d = new Date(date)
      const year = d.getFullYear()
      const month = String(d.getMonth() + 1).padStart(2, '0')
      const day = String(d.getDate()).padStart(2, '0')
      return `${year}-${month}-${day}`
    },
    /** 作废BOM */
    handleCancel(row) {
      this.$modal.confirm('是否确认作废该BOM？').then(() => {
        const data = {
          id: row.id,
          approvalStatus: '3'
        }
        updateBom(data).then(response => {
          this.$modal.msgSuccess("作废成功")
          this.getList()
        })
      }).catch(() => {})
    }
  }
}
</script>
