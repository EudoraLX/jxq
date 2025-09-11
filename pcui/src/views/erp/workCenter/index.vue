<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="中心编码" prop="workCenterCode">
        <el-input
          v-model="queryParams.workCenterCode"
          placeholder="请输入中心编码"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="中心名称" prop="workCenterName">
        <el-input
          v-model="queryParams.workCenterName"
          placeholder="请输入中心名称"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="中心类型" prop="workCenterType">
        <el-select v-model="queryParams.workCenterType" placeholder="请选择中心类型" clearable>
          <el-option
            v-for="dict in dict.type.work_center_type"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="部门" prop="deptId">
        <treeselect 
          v-model="queryParams.deptId" 
          :options="deptOptions" 
          :show-count="true" 
          placeholder="请选择部门" 
          style="width: 200px" 
          clearable 
          :disable-branch-nodes="true"
          :normalizer="normalizer"
        />
      </el-form-item>
      <el-form-item label="产能类型" prop="capacityType">
        <el-select v-model="queryParams.capacityType" placeholder="请选择产能类型" clearable>
          <el-option
            v-for="dict in dict.type.capacity_type"
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
          v-hasPermi="['erp:workCenter:add']"
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
          v-hasPermi="['erp:workCenter:edit']"
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
          v-hasPermi="['erp:workCenter:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['erp:workCenter:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="workCenterList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <!-- <el-table-column label="工作中心ID" align="center" prop="id" /> -->
      <el-table-column label="中心编码" align="center" prop="workCenterCode" />
      <el-table-column label="中心名称" align="center" prop="workCenterName" />
      <el-table-column label="中心类型" align="center" prop="workCenterType">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.work_center_type" :value="scope.row.workCenterType"/>
        </template>
      </el-table-column>
      <el-table-column label="部门" align="center" prop="dept.deptName" :show-overflow-tooltip="true" />
      <el-table-column label="位置" align="center" prop="location" />
      <el-table-column label="产能类型" align="center" prop="capacityType">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.capacity_type" :value="scope.row.capacityType"/>
        </template>
      </el-table-column>
      <el-table-column label="标准产能" align="center" prop="standardCapacity" />
      <el-table-column label="产能单位" align="center" prop="capacityUnit">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.capacity_unit" :value="scope.row.capacityUnit"/>
        </template>
      </el-table-column>
      <el-table-column label="效率系数" align="center" prop="efficiencyRate" />
      <el-table-column label="可用产能" align="center" prop="availableCapacity" />
      <el-table-column label="准备时间(分钟)" align="center" prop="setupTime" width="120" />
      <el-table-column label="加工时间(分钟)" align="center" prop="processingTime" width="120" />
      <el-table-column label="移动时间(分钟)" align="center" prop="moveTime" width="120" />
      <el-table-column label="排队时间(分钟)" align="center" prop="queueTime" width="120" />
      <el-table-column label="周期时间(分钟)" align="center" prop="cycleTime" width="120" />
      <el-table-column label="是否启用" align="center" prop="isActive">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.business_active" :value="scope.row.isActive"/>
        </template>
      </el-table-column>
<!--      <el-table-column label="条码" align="center" prop="workCenterCode" width="120">-->
<!--        <template slot-scope="scope">-->
<!--          <barcode-display-->
<!--            v-if="scope.row.workCenterCode"-->
<!--            :value="scope.row.workCenterCode"-->
<!--            :width="1.5"-->
<!--            :height="40"-->
<!--            format="CODE128"-->
<!--          />-->
<!--        </template>-->
<!--      </el-table-column>-->
<!--      <el-table-column label="备注" align="center" prop="remark" />-->
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['erp:workCenter:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['erp:workCenter:remove']"
          >删除</el-button>
          <barcode-printer
            :machine-data="getBarcodeData(scope.row)"
            title="工作中心标签"
            label-title="工作中心标签"
            :info-labels="barcodeLabels"
            :machine-type-dict="dict.type.work_center_type"
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

    <!-- 添加或修改工作中心对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="90%" append-to-body>
      <el-tabs v-model="activeTab" type="border-card">
        <el-tab-pane label="基本信息" name="basic">
          <el-form ref="form" :model="form" :rules="rules" label-width="120px" style="padding: 20px;">
            <el-row :gutter="20">
              <el-col :span="12">
                <el-form-item label="中心编码" prop="workCenterCode">
                  <el-input v-model="form.workCenterCode" placeholder="请输入中心编码" prefix-icon="el-icon-document" />
                </el-form-item>
              </el-col>
              <el-col :span="12">
                <el-form-item label="中心名称" prop="workCenterName">
                  <el-input v-model="form.workCenterName" placeholder="请输入中心名称" prefix-icon="el-icon-edit" />
                </el-form-item>
              </el-col>
            </el-row>

            <el-row :gutter="20">
              <el-col :span="12">
                <el-form-item label="中心类型" prop="workCenterType">
                  <el-select v-model="form.workCenterType" placeholder="请选择中心类型" style="width:100%">
                    <el-option v-for="dict in dict.type.work_center_type" :key="dict.value" :label="dict.label" :value="dict.value" />
                  </el-select>
                </el-form-item>
              </el-col>
              <el-col :span="12">
                <el-form-item label="归属部门" prop="deptId">
                  <treeselect 
                    v-model="form.deptId" 
                    :options="enabledDeptOptions" 
                    :show-count="true" 
                    placeholder="请选择归属部门" 
                    :disable-branch-nodes="true"
                    :normalizer="normalizer"
                  />
                </el-form-item>
              </el-col>
            </el-row>

            <el-row :gutter="20">
              <el-col :span="12">
                <el-form-item label="位置" prop="location">
                  <el-input v-model="form.location" placeholder="请输入位置" prefix-icon="el-icon-location" />
                </el-form-item>
              </el-col>
              <el-col :span="12">
                <el-form-item label="是否启用" prop="isActive">
                  <el-select v-model="form.isActive" placeholder="请选择是否启用" style="width:100%">
                    <el-option v-for="dict in dict.type.business_active" :key="dict.value" :label="dict.label" :value="dict.value" />
                  </el-select>
                </el-form-item>
              </el-col>
            </el-row>
          </el-form>
        </el-tab-pane>

        <el-tab-pane label="产能配置" name="capacity">
          <el-form :model="form" label-width="120px" style="padding: 20px;">
            <el-row :gutter="20">
              <el-col :span="12">
                <el-form-item label="产能类型" prop="capacityType">
                  <el-select v-model="form.capacityType" placeholder="请选择产能类型" style="width:100%">
                    <el-option v-for="dict in dict.type.capacity_type" :key="dict.value" :label="dict.label" :value="dict.value" />
                  </el-select>
                </el-form-item>
              </el-col>
              <el-col :span="12">
                <el-form-item label="产能单位" prop="capacityUnit">
                  <el-select v-model="form.capacityUnit" placeholder="请选择产能单位" style="width:100%">
                    <el-option v-for="dict in dict.type.capacity_unit" :key="dict.value" :label="dict.label" :value="dict.value" />
                  </el-select>
                </el-form-item>
              </el-col>
            </el-row>

            <el-row :gutter="20">
              <el-col :span="12">
                <el-form-item prop="standardCapacity">
                  <span slot="label">
                    标准产能
                    <el-tooltip content="标准产能表示每天理论可工作时长" placement="top">
                      <i class="el-icon-question" style="color: #409EFF; margin-left: 4px; cursor: help;"></i>
                    </el-tooltip>
                  </span>
                  <el-input-number v-model="form.standardCapacity" :precision="2" :step="1" placeholder="请输入标准产能" style="width: 100%;" :min="0" @input="calculateAvailableCapacity" />
                </el-form-item>
              </el-col>
              <el-col :span="12">
                <el-form-item prop="availableCapacity">
                  <span slot="label">
                    可用产能
                    <el-tooltip content="可用产能表示每天预计可工作时长" placement="top">
                      <i class="el-icon-question" style="color: #409EFF; margin-left: 4px; cursor: help;"></i>
                    </el-tooltip>
                  </span>
                  <el-input-number v-model="form.availableCapacity" :precision="2" :step="1" placeholder="可用产能" style="width: 100%;" :min="0" :disabled="true" />
                </el-form-item>
              </el-col>
            </el-row>

            <el-row :gutter="20">
              <el-col :span="12">
                <el-form-item label="效率系数" prop="efficiencyRate">
                  <el-input-number v-model="form.efficiencyRate" :precision="3" :step="0.01" placeholder="请输入效率系数" style="width: 100%;" :min="0" :max="2" @input="calculateAvailableCapacity" />
                </el-form-item>
              </el-col>
              <el-col :span="12">
                <!-- 空列 -->
              </el-col>
            </el-row>

            <el-row :gutter="20">
              <el-col :span="12">
                <el-form-item label="准备时间(分钟)" prop="setupTime">
                  <el-input-number v-model="form.setupTime" :precision="2" :step="0.01" placeholder="请输入准备时间" style="width: 100%;" :min="0" @input="calculateCycleTime" />
                </el-form-item>
              </el-col>
              <el-col :span="12">
                <el-form-item label="加工时间(分钟)" prop="processingTime">
                  <el-input-number v-model="form.processingTime" :precision="2" :step="0.01" placeholder="请输入加工时间" style="width: 100%;" :min="0" @input="calculateCycleTime" />
                </el-form-item>
              </el-col>
            </el-row>

            <el-row :gutter="20">
              <el-col :span="12">
                <el-form-item label="移动时间(分钟)" prop="moveTime">
                  <el-input-number v-model="form.moveTime" :precision="2" :step="0.01" placeholder="请输入移动时间" style="width: 100%;" :min="0" @input="calculateCycleTime" />
                </el-form-item>
              </el-col>
              <el-col :span="12">
                <el-form-item label="排队时间(分钟)" prop="queueTime">
                  <el-input-number v-model="form.queueTime" :precision="2" :step="0.01" placeholder="请输入排队时间" style="width: 100%;" :min="0" @input="calculateCycleTime" />
                </el-form-item>
              </el-col>
            </el-row>

            <el-row :gutter="20">
              <el-col :span="12">
                <el-form-item label="周期时间(分钟)" prop="cycleTime">
                  <el-input-number v-model="form.cycleTime" :precision="2" :step="0.01" placeholder="自动计算" style="width: 100%;" :min="0" :disabled="true" />
                </el-form-item>
              </el-col>
              <el-col :span="12">
                <!-- 空列 -->
              </el-col>
            </el-row>

            <!-- 配置管理部分 -->
            <el-divider content-position="left">配置管理</el-divider>
            <div style="margin-bottom: 20px;">
              <el-button type="primary" size="small" @click="handleAddConfig" v-hasPermi="['erp:workCenter:edit']">
                <i class="el-icon-plus"></i> 添加配置
              </el-button>
            </div>

            <el-table :data="form.configList" border style="width: 100%">
              <el-table-column label="设备" align="center" prop="equipmentName" width="150" />
              <el-table-column label="模具" align="center" prop="moldName" width="150" />
              <el-table-column label="产能类型" align="center" prop="capacityType" width="120">
                <template slot-scope="scope">
                  <dict-tag :options="dict.type.capacity_type" :value="scope.row.capacityType"/>
                </template>
              </el-table-column>
              <el-table-column label="标准产能" align="center" prop="standardCapacity" width="120" />
              <el-table-column label="产能单位" align="center" prop="capacityUnit" width="100">
                <template slot-scope="scope">
                  <dict-tag :options="dict.type.capacity_unit" :value="scope.row.capacityUnit"/>
                </template>
              </el-table-column>
              <el-table-column label="效率系数" align="center" prop="efficiencyRate" width="100" />
              <el-table-column label="可用产能" align="center" prop="availableCapacity" width="120" />
              <el-table-column label="准备时间(分钟)" align="center" prop="setupTime" width="120" />
              <el-table-column label="加工时间(分钟)" align="center" prop="processingTime" width="120" />
              <el-table-column label="移动时间(分钟)" align="center" prop="moveTime" width="120" />
              <el-table-column label="排队时间(分钟)" align="center" prop="queueTime" width="120" />
              <el-table-column label="周期时间(分钟)" align="center" prop="cycleTime" width="120" />
              <el-table-column label="是否首选" align="center" prop="isPrimary" width="100">
                <template slot-scope="scope">
                  <el-switch v-model="scope.row.isPrimary" active-value="1" inactive-value="0" @change="handlePrimaryChange(scope.row, scope.$index)"></el-switch>
                </template>
              </el-table-column>
              <el-table-column label="操作" align="center" class-name="small-padding fixed-width" width="120">
                <template slot-scope="scope">
                  <el-button
                    size="mini"
                    type="text"
                    icon="el-icon-edit"
                    @click="handleEditConfig(scope.$index)"
                    v-hasPermi="['erp:workCenter:edit']"
                  >修改</el-button>
                  <el-button
                    size="mini"
                    type="text"
                    icon="el-icon-delete"
                    @click="handleDeleteConfig(scope.$index)"
                    v-hasPermi="['erp:workCenter:edit']"
                  >删除</el-button>
                </template>
              </el-table-column>
            </el-table>
          </el-form>
        </el-tab-pane>

        <el-tab-pane label="成本与时间" name="cost">
          <el-form :model="form" label-width="120px" style="padding: 20px;">
            <el-row :gutter="20">
              <el-col :span="12">
                <el-form-item label="成本中心" prop="costCenter">
                  <el-input v-model="form.costCenter" placeholder="请输入成本中心" prefix-icon="el-icon-coin" />
                </el-form-item>
              </el-col>
              <el-col :span="12">
                <el-form-item label="小时成本" prop="costPerHour">
                  <el-input-number v-model="form.costPerHour" :precision="2" :step="10" placeholder="请输入小时成本" style="width: 100%;" :min="0" />
                </el-form-item>
              </el-col>
            </el-row>


          </el-form>
        </el-tab-pane>


        <el-tab-pane label="其他" name="other">
          <el-form :model="form" label-width="120px" style="padding: 20px;">
            <el-row :gutter="20">
              <el-col :span="24">
                <el-form-item label="备注" prop="remark">
                  <el-input v-model="form.remark" type="textarea" placeholder="请输入备注信息" />
                </el-form-item>
              </el-col>
            </el-row>
          </el-form>
        </el-tab-pane>
      </el-tabs>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>

    <!-- 配置编辑对话框 -->
    <el-dialog :title="configTitle" :visible.sync="configOpen" width="600px" append-to-body>
      <el-form ref="configForm" :model="configForm" :rules="configRules" label-width="120px">
        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item label="设备" prop="equipmentId">
              <el-select v-model="configForm.equipmentId" placeholder="请选择设备" style="width:100%" @change="handleEquipmentChange">
                <el-option v-for="equipment in equipmentList" :key="equipment.id" :label="equipment.equipmentName" :value="equipment.id" />
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="模具" prop="moldId">
              <el-select v-model="configForm.moldId" placeholder="请选择模具" style="width:100%" @change="handleMoldChange">
                <el-option v-for="mold in moldList" :key="mold.id" :label="mold.moldName" :value="mold.id" />
              </el-select>
            </el-form-item>
          </el-col>
        </el-row>

        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item label="产能类型" prop="capacityType">
              <el-select v-model="configForm.capacityType" placeholder="请选择产能类型" style="width:100%">
                <el-option v-for="dict in dict.type.capacity_type" :key="dict.value" :label="dict.label" :value="dict.value" />
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="产能单位" prop="capacityUnit">
              <el-select v-model="configForm.capacityUnit" placeholder="请选择产能单位" style="width:100%" @change="handleConfigFieldChange">
                <el-option v-for="dict in dict.type.capacity_unit" :key="dict.value" :label="dict.label" :value="dict.value" />
              </el-select>
            </el-form-item>
          </el-col>
        </el-row>

        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item prop="standardCapacity">
              <span slot="label">
                标准产能
                <el-tooltip content="标准产能表示每天理论可工作时长" placement="top">
                  <i class="el-icon-question" style="color: #409EFF; margin-left: 4px; cursor: help;"></i>
                </el-tooltip>
              </span>
              <el-input-number v-model="configForm.standardCapacity" :precision="4" :step="0.0001" placeholder="自动计算" style="width: 100%;" :min="0" :disabled="true" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item prop="availableCapacity">
              <span slot="label">
                可用产能
                <el-tooltip content="可用产能表示每天预计可工作时长" placement="top">
                  <i class="el-icon-question" style="color: #409EFF; margin-left: 4px; cursor: help;"></i>
                </el-tooltip>
              </span>
              <el-input-number v-model="configForm.availableCapacity" :precision="2" :step="1" placeholder="可用产能" style="width: 100%;" :min="0" :disabled="true" />
            </el-form-item>
          </el-col>
        </el-row>

        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item label="效率系数" prop="efficiencyRate">
              <el-input-number v-model="configForm.efficiencyRate" :precision="3" :step="0.01" placeholder="请输入效率系数" style="width: 100%;" :min="0" :max="2" @input="handleConfigFieldChange" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="是否首选" prop="isPrimary">
              <el-switch v-model="configForm.isPrimary" active-value="1" inactive-value="0" active-text="是" inactive-text="否"></el-switch>
            </el-form-item>
          </el-col>
        </el-row>

        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item label="准备时间(分钟)" prop="setupTime">
              <el-input-number v-model="configForm.setupTime" :precision="2" :step="0.01" placeholder="请输入准备时间" style="width: 100%;" :min="0" @input="handleConfigFieldChange" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="加工时间(分钟)" prop="processingTime">
              <el-input-number v-model="configForm.processingTime" :precision="2" :step="0.01" placeholder="请输入加工时间" style="width: 100%;" :min="0" @input="handleConfigFieldChange" />
            </el-form-item>
          </el-col>
        </el-row>

        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item label="移动时间(分钟)" prop="moveTime">
              <el-input-number v-model="configForm.moveTime" :precision="2" :step="0.01" placeholder="请输入移动时间" style="width: 100%;" :min="0" @input="handleConfigFieldChange" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="排队时间(分钟)" prop="queueTime">
              <el-input-number v-model="configForm.queueTime" :precision="2" :step="0.01" placeholder="请输入排队时间" style="width: 100%;" :min="0" @input="handleConfigFieldChange" />
            </el-form-item>
          </el-col>
        </el-row>

        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item label="周期时间(分钟)" prop="cycleTime">
              <el-input-number v-model="configForm.cycleTime" :precision="2" :step="0.01" placeholder="自动计算" style="width: 100%;" :min="0" :disabled="true" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <!-- 空列 -->
          </el-col>
        </el-row>

        <el-row :gutter="20">
          <el-col :span="24">
            <el-form-item label="备注" prop="remark">
              <el-input v-model="configForm.remark" type="textarea" placeholder="请输入备注信息" />
            </el-form-item>
          </el-col>
        </el-row>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitConfigForm">确 定</el-button>
        <el-button @click="cancelConfig">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { listWorkCenter, getWorkCenter, delWorkCenter, addWorkCenter, updateWorkCenter } from "@/api/erp/workCenter"
import { listEquipment } from "@/api/erp/equipment"
import { listMold } from "@/api/erp/mold"
import { deptTreeSelect } from "@/api/system/user"
import Treeselect from "@riophae/vue-treeselect"
import "@riophae/vue-treeselect/dist/vue-treeselect.css"
import BarcodeDisplay from "@/components/BarcodeDisplay"
import BarcodePrinter from "@/components/BarcodePrinter"

export default {
  name: "WorkCenter",
  dicts: ['work_center_type', 'business_active', 'capacity_type', 'capacity_unit'],
  components: {
    Treeselect,
    BarcodeDisplay,
    BarcodePrinter
  },
  data() {
    return {
      // 遮罩层
      loading: true,
      // 选中数组
      ids: [],
      // 非单个禁用
      single: true,
      // 非多个禁用
      multiple: true,
      // 显示搜索条件
      showSearch: true,
      // 总条数
      total: 0,
      // 工作中心表格数据
      workCenterList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 当前激活的Tab
      activeTab: "basic",
      // 所有部门树选项
      deptOptions: [],
      // 过滤掉已禁用部门树选项
      enabledDeptOptions: [],
      // 条码标签配置
      barcodeLabels: {
        code: '中心编码',
        name: '中心名称',
        type: '中心类型',
        workCenter: '所属部门'
      },
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        workCenterCode: null,
        workCenterName: null,
        workCenterType: null,
        deptId: null,
        capacityType: null,
        isActive: null,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        workCenterCode: [
          { required: true, message: "中心编码不能为空", trigger: "blur" }
        ],
        workCenterName: [
          { required: true, message: "中心名称不能为空", trigger: "blur" }
        ],
        isActive: [
          { required: true, message: "是否启用不能为空", trigger: "change" }
        ],
      },
      // 配置相关
      configOpen: false,
      configTitle: "",
      configForm: {},
      configRules: {
        equipmentId: [
          { required: true, message: "设备不能为空", trigger: "change" }
        ],
        capacityType: [
          { required: true, message: "产能类型不能为空", trigger: "change" }
        ],
        standardCapacity: [
          { required: true, message: "标准产能不能为空", trigger: "blur" }
        ],
        capacityUnit: [
          { required: true, message: "产能单位不能为空", trigger: "blur" }
        ]
      },
      equipmentList: [],
      moldList: [],
      currentConfigIndex: -1
    }
  },
  created() {
    this.getList()
    this.getDeptTree()
    this.getEquipmentList()
    this.getMoldList()
  },
  methods: {
    /** 获取条码数据 */
    getBarcodeData(row) {
      return {
        machineCode: row.workCenterCode,
        machineName: row.workCenterName,
        machineType: row.workCenterType,
        workCenterName: (row.dept && row.dept.deptName) || '未设置部门'
      }
    },
    /** 自定义节点标签和值的属性 */
    normalizer(node) {
      return {
        id: node.id,
        label: node.label,
        children: node.children
      }
    },
    /** 查询工作中心列表 */
    getList() {
      this.loading = true
      listWorkCenter(this.queryParams).then(response => {
        this.workCenterList = response.rows
        this.total = response.total
        this.loading = false
      })
    },
    /** 查询部门下拉树结构 */
    getDeptTree() {
      deptTreeSelect().then(response => {
        this.deptOptions = response.data
        this.enabledDeptOptions = this.filterDisabledDept(JSON.parse(JSON.stringify(response.data)))
      })
    },
    // 过滤禁用的部门
    filterDisabledDept(deptList) {
      return deptList.filter(dept => {
        if (dept.disabled) {
          return false
        }
        if (dept.children && dept.children.length) {
          dept.children = this.filterDisabledDept(dept.children)
        }
        return true
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
        workCenterCode: null,
        workCenterName: null,
        workCenterType: null,
        deptId: null,
        location: null,
        capacityType: null,
        standardCapacity: null,
        capacityUnit: 'h',
        efficiencyRate: null,
        availableCapacity: null,
        costCenter: null,
        costPerHour: null,
        setupTime: null,
        processingTime: null,
        moveTime: null,
        queueTime: null,
        cycleTime: null,
        isActive: null,
        remark: null,
        createBy: null,
        createTime: null,
        updateBy: null,
        updateTime: null,
        delFlag: null,
        configList: []
      }
      this.activeTab = "basic"
      this.resetForm("form")
      // 计算可用产能
      this.calculateAvailableCapacity()
      // 计算周期时间
      this.calculateCycleTime()
    },
    /** 搜索按钮操作 */
    handleQuery() {
      this.queryParams.pageNum = 1
      this.getList()
    },
    /** 重置按钮操作 */
    resetQuery() {
      this.resetForm("queryForm")
      this.queryParams.deptId = null
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
      this.title = "添加工作中心"
      this.activeTab = "basic"
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset()
      const id = row.id || this.ids
      getWorkCenter(id).then(response => {
        this.form = response.data
        this.open = true
        this.title = "修改工作中心"
        this.activeTab = "basic"
        // 计算可用产能
        this.calculateAvailableCapacity()
        // 计算周期时间
        this.calculateCycleTime()
        // 检查是否有首选配置，如果有则同步到主表
        this.syncPrimaryConfigFromList()
      })
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != null) {
            updateWorkCenter(this.form).then(response => {
              this.$modal.msgSuccess("修改成功")
              this.open = false
              this.getList()
            })
          } else {
            addWorkCenter(this.form).then(response => {
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
      this.$modal.confirm('是否确认删除工作中心编号为"' + ids + '"的数据项？').then(function() {
        return delWorkCenter(ids)
      }).then(() => {
        this.getList()
        this.$modal.msgSuccess("删除成功")
      }).catch(() => {})
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('erp/workCenter/export', {
        ...this.queryParams
      }, `workCenter_${new Date().getTime()}.xlsx`)
    },
    /** 获取设备列表 */
    getEquipmentList() {
      listEquipment({ isActive: '1' }).then(response => {
        this.equipmentList = response.rows || []
      }).catch(() => {
        this.equipmentList = []
      })
    },
    /** 获取模具列表 */
    getMoldList() {
      listMold({ isActive: '1' }).then(response => {
        this.moldList = response.rows || []
      }).catch(() => {
        this.moldList = []
      })
    },
    /** 添加配置 */
    handleAddConfig() {
      this.resetConfigForm()
      this.configTitle = "添加配置"
      this.configOpen = true
      this.currentConfigIndex = -1
    },
    /** 修改配置 */
    handleEditConfig(index) {
      this.configForm = Object.assign({}, this.form.configList[index])
      this.configTitle = "修改配置"
      this.configOpen = true
      this.currentConfigIndex = index
      // 计算可用产能
      this.calculateConfigAvailableCapacity()
      // 计算周期时间
      this.calculateConfigCycleTime()
    },
    /** 删除配置 */
    handleDeleteConfig(index) {
      this.$modal.confirm('是否确认删除该配置？').then(() => {
        this.form.configList.splice(index, 1)
        this.$modal.msgSuccess("删除成功")
      }).catch(() => {})
    },
    /** 提交配置表单 */
    submitConfigForm() {
      this.$refs["configForm"].validate(valid => {
        if (valid) {
          if (this.currentConfigIndex === -1) {
            // 新增配置
            this.form.configList.push(Object.assign({}, this.configForm))
          } else {
            // 修改配置
            this.$set(this.form.configList, this.currentConfigIndex, Object.assign({}, this.configForm))
          }

          // 检查是否为首选配置，如果是则同步到主表
          if (this.configForm.isPrimary === '1') {
            if (this.currentConfigIndex === -1) {
              // 新增配置：将其他现有配置的首选状态设为"0"
              this.form.configList.forEach((config, index) => {
                if (index !== this.form.configList.length - 1) { // 排除刚添加的配置
                  config.isPrimary = '0';
                }
              });
            } else {
              // 修改配置：将其他配置的首选状态设为"0"
              this.form.configList.forEach((config, index) => {
                if (index !== this.currentConfigIndex) {
                  config.isPrimary = '0';
                }
              });
            }
            // 同步首选配置到主表
            this.syncPrimaryConfigToMainForm()
          }

          this.configOpen = false
          this.$modal.msgSuccess("操作成功")
        }
      })
    },
    /** 取消配置 */
    cancelConfig() {
      this.configOpen = false
      this.resetConfigForm()
    },
    /** 重置配置表单 */
    resetConfigForm() {
      this.configForm = {
        equipmentId: null,
        equipmentCode: '',
        equipmentName: '',
        moldId: null,
        moldCode: '',
        moldName: '',
        capacityType: '',
        standardCapacity: null,
        capacityUnit: 'h',
        efficiencyRate: 1.0,
        availableCapacity: null,
        setupTime: null,
        processingTime: null,
        moveTime: null,
        queueTime: null,
        cycleTime: null,
        isPrimary: '0',
        remark: ''
      }
      // 计算可用产能
      this.calculateConfigAvailableCapacity()
      // 计算周期时间
      this.calculateConfigCycleTime()
    },
    /** 设备选择变化 */
    handleEquipmentChange(value) {
      const equipment = this.equipmentList.find(item => item.id === value)
      if (equipment) {
        this.configForm.equipmentCode = equipment.equipmentCode || ''
        this.configForm.equipmentName = equipment.equipmentName
      }
    },
    /** 模具选择变化 */
    handleMoldChange(value) {
      const mold = this.moldList.find(item => item.id === value)
      if (mold) {
        this.configForm.moldCode = mold.moldCode || ''
        this.configForm.moldName = mold.moldName
        // 模具变化时重新计算产能
        this.calculateConfigAvailableCapacity()
      }
    },
    /** 配置字段变化处理 */
    handleConfigFieldChange() {
      // 重新计算配置的产能
      this.calculateConfigAvailableCapacity()

      // 如果当前配置是首选配置，同步到主表
      if (this.configForm.isPrimary === '1') {
        this.syncPrimaryConfigToMainForm()
      }
    },
    /** 计算主表可用产能 */
    calculateAvailableCapacity() {
      const standardCapacity = parseFloat(this.form.standardCapacity) || 0;
      const efficiencyRate = parseFloat(this.form.efficiencyRate) || 0;
      const availableCapacity = standardCapacity * efficiencyRate;
      this.form.availableCapacity = parseFloat(availableCapacity.toFixed(2));
    },
    /** 计算配置表可用产能 */
    calculateConfigAvailableCapacity() {
      // 先计算周期时间
      this.calculateConfigCycleTime();

      // 根据模具型腔数量计算标准产能
      this.calculateConfigStandardCapacity();

      // 计算可用产能
      const standardCapacity = parseFloat(this.configForm.standardCapacity) || 0;
      const efficiencyRate = parseFloat(this.configForm.efficiencyRate) || 0;
      const availableCapacity = standardCapacity * efficiencyRate;
      this.configForm.availableCapacity = parseFloat(availableCapacity.toFixed(2));
    },
    /** 计算主表周期时间 */
    calculateCycleTime() {
      const setupTime = parseFloat(this.form.setupTime) || 0;
      const processingTime = parseFloat(this.form.processingTime) || 0;
      const moveTime = parseFloat(this.form.moveTime) || 0;
      const queueTime = parseFloat(this.form.queueTime) || 0;
      const cycleTime = setupTime + processingTime + moveTime + queueTime;
      this.form.cycleTime = parseFloat(cycleTime.toFixed(2));
    },
    /** 计算配置表周期时间 */
    calculateConfigCycleTime() {
      const setupTime = parseFloat(this.configForm.setupTime) || 0;
      const processingTime = parseFloat(this.configForm.processingTime) || 0;
      const moveTime = parseFloat(this.configForm.moveTime) || 0;
      const queueTime = parseFloat(this.configForm.queueTime) || 0;
      const cycleTime = setupTime + processingTime + moveTime + queueTime;
      this.configForm.cycleTime = parseFloat(cycleTime.toFixed(2));
    },
    /** 计算配置表标准产能 */
    calculateConfigStandardCapacity() {
      // 获取模具型腔数量
      const selectedMold = this.moldList.find(mold => mold.id === this.configForm.moldId);
      const cavityCount = selectedMold ? (parseInt(selectedMold.cavityCount) || 1) : 1;

      // 获取周期时间
      const cycleTime = parseFloat(this.configForm.cycleTime) || 0;

      // 根据产能单位计算标准产能
      const capacityUnit = this.configForm.capacityUnit;
      let standardCapacity = 0;

      if (capacityUnit === 'h') {
        // 若产能单位为 "h"，则标准产能 = 周期时间(分钟) * 型腔数量 / 60
        standardCapacity = (cycleTime * cavityCount) / 60;
      } else if (capacityUnit === 'd') {
        // 若产能单位为 "d"，则标准产能 = 周期时间(分钟) * 型腔数量 / 60 / 24
        standardCapacity = (cycleTime * cavityCount) / 60 / 24;
      }

      this.configForm.standardCapacity = parseFloat(standardCapacity.toFixed(4));
    },
        /** 同步首选配置到主表 */
    syncPrimaryConfigToMainForm() {
      // 将首选配置的产能信息同步到主表
      this.form.capacityType = this.configForm.capacityType;
      this.form.standardCapacity = this.configForm.standardCapacity;
      this.form.capacityUnit = this.configForm.capacityUnit;
      this.form.efficiencyRate = this.configForm.efficiencyRate;
      this.form.availableCapacity = this.configForm.availableCapacity;
      this.form.setupTime = this.configForm.setupTime;
      this.form.processingTime = this.configForm.processingTime;
      this.form.moveTime = this.configForm.moveTime;
      this.form.queueTime = this.configForm.queueTime;
      this.form.cycleTime = this.configForm.cycleTime;

      // 重新计算主表的可用产能和周期时间
      this.calculateAvailableCapacity();
      this.calculateCycleTime();
    },
    /** 处理首选状态变更 */
    handlePrimaryChange(row, index) {
      let text = row.isPrimary === "1" ? "设置为首选" : "取消首选"
      this.$modal.confirm('确认要"' + text + '"该配置吗？').then(() => {
        if (row.isPrimary === "1") {
          // 设置为首选：将其他配置的首选状态设为"0"
          this.form.configList.forEach((config, configIndex) => {
            if (configIndex !== index) {
              config.isPrimary = '0';
            }
          });
          // 同步首选配置到主表
          this.syncPrimaryConfigFromList();
        }
        this.$modal.msgSuccess(text + "成功")
      }).catch(() => {
        // 取消操作，恢复原状态
        row.isPrimary = row.isPrimary === "1" ? "0" : "1"
      })
    },
    /** 从配置列表中同步首选配置到主表 */
    syncPrimaryConfigFromList() {
      if (this.form.configList && this.form.configList.length > 0) {
        // 查找首选配置
        const primaryConfig = this.form.configList.find(config => config.isPrimary === '1');
        if (primaryConfig) {
          // 将首选配置的产能信息同步到主表
          this.form.capacityType = primaryConfig.capacityType;
          this.form.standardCapacity = primaryConfig.standardCapacity;
          this.form.capacityUnit = primaryConfig.capacityUnit;
          this.form.efficiencyRate = primaryConfig.efficiencyRate;
          this.form.availableCapacity = primaryConfig.availableCapacity;
          this.form.setupTime = primaryConfig.setupTime;
          this.form.processingTime = primaryConfig.processingTime;
                    this.form.moveTime = primaryConfig.moveTime;
          this.form.queueTime = primaryConfig.queueTime;
          this.form.cycleTime = primaryConfig.cycleTime;

          // 重新计算主表的可用产能和周期时间
          this.calculateAvailableCapacity();
          this.calculateCycleTime();
        }
      }
    }
  }
}
</script>
