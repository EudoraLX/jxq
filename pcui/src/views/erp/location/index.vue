<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="货位编码" prop="locationCode">
        <el-input
          v-model="queryParams.locationCode"
          placeholder="请输入货位编码"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="货位名称" prop="locationName">
        <el-input
          v-model="queryParams.locationName"
          placeholder="请输入货位名称"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="所属仓库" prop="warehouseId">
        <el-select v-model="queryParams.warehouseId" placeholder="请选择所属仓库" clearable @change="handleWarehouseChange">
          <el-option
            v-for="warehouse in warehouseList"
            :key="warehouse.id"
            :label="warehouse.warehouseName"
            :value="warehouse.id"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="所属货架" prop="shelfId">
        <el-select v-model="queryParams.shelfId" placeholder="请选择所属货架" clearable>
          <el-option
            v-for="shelf in shelfList"
            :key="shelf.id"
            :label="shelf.shelfName"
            :value="shelf.id"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="货位类型" prop="locationType">
        <el-select v-model="queryParams.locationType" placeholder="请选择货位类型" clearable>
          <el-option
            v-for="dict in dict.type.location_type"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="占用状态" prop="isOccupied">
        <el-select v-model="queryParams.isOccupied" placeholder="请选择占用状态" clearable>
          <el-option
            v-for="dict in dict.type.location_is_occupied"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="状态" prop="isActive">
        <el-select v-model="queryParams.isActive" placeholder="请选择状态" clearable>
          <el-option
            v-for="dict in dict.type.sys_yes_no"
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
          v-hasPermi="['erp:location:add']"
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
          v-hasPermi="['erp:location:edit']"
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
          v-hasPermi="['erp:location:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['erp:location:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="locationList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="货位编码" align="center" prop="locationCode">
        <template slot-scope="scope">
          <div>
            <div>{{ scope.row.locationCode }}</div>
            <barcode-display
              v-if="scope.row.locationCode"
              :value="scope.row.locationCode"
              :width="1.5"
              :height="30"
              format="CODE128"
            />
          </div>
        </template>
      </el-table-column>
      <el-table-column label="货位名称" align="center" prop="locationName" />
      <el-table-column label="所属仓库" align="center" prop="warehouseName" />
      <el-table-column label="所属货架" align="center" prop="shelfName" />
      <el-table-column label="货位类型" align="center" prop="locationType">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.location_type" :value="scope.row.locationType"/>
        </template>
      </el-table-column>
      <el-table-column label="层级" align="center" prop="levelNum" />
      <el-table-column label="位置" align="center" prop="positionNum" />
      <el-table-column label="容量" align="center" prop="capacity" />
      <el-table-column label="已用容量" align="center" prop="usedCapacity" />
      <el-table-column label="占用状态" align="center" prop="isOccupied">
        <template slot-scope="scope">
          <el-switch
            v-model="scope.row.isOccupied"
            :active-value="'1'"
            :inactive-value="'0'"
            @change="handleOccupiedChange(scope.row)"
            v-hasPermi="['erp:location:edit']"
          />
        </template>
      </el-table-column>
      <el-table-column label="状态" align="center" prop="isActive">
        <template slot-scope="scope">
          <el-switch
            v-model="scope.row.isActive"
            :active-value="'1'"
            :inactive-value="'0'"
            @change="handleStatusChange(scope.row)"
            v-hasPermi="['erp:location:edit']"
          />
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['erp:location:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['erp:location:remove']"
          >删除</el-button>
          <barcode-printer 
            :machine-data="{
              machineCode: scope.row.locationCode,
              machineName: scope.row.locationName,
              machineType: scope.row.locationType,
              workCenterName: scope.row.shelfName || '未设置货架'
            }"
            :title="'货位标签'"
            :label-title="'货位标签'"
            :info-labels="{
              code: '货位编码',
              name: '货位名称',
              type: '货位类型',
              workCenter: '所属货架'
            }"
            :machine-type-dict="dict.type.location_type"
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

    <!-- 添加或修改货位信息对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="600px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="货位编码" prop="locationCode">
          <el-input v-model="form.locationCode" placeholder="请输入货位编码" />
        </el-form-item>
        <el-form-item label="货位名称" prop="locationName">
          <el-input v-model="form.locationName" placeholder="请输入货位名称" />
        </el-form-item>
        <el-form-item label="所属仓库" prop="warehouseId">
          <el-select v-model="form.warehouseId" placeholder="请选择所属仓库" @change="handleFormWarehouseChange">
            <el-option
              v-for="warehouse in warehouseList"
              :key="warehouse.id"
              :label="warehouse.warehouseName"
              :value="warehouse.id"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="所属货架" prop="shelfId">
          <el-select v-model="form.shelfId" placeholder="请选择所属货架">
            <el-option
              v-for="shelf in formShelfList"
              :key="shelf.id"
              :label="shelf.shelfName"
              :value="shelf.id"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="货位类型" prop="locationType">
          <el-select v-model="form.locationType" placeholder="请选择货位类型">
            <el-option
              v-for="dict in dict.type.location_type"
              :key="dict.value"
              :label="dict.label"
              :value="dict.value"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="层级" prop="levelNum">
          <el-input-number v-model="form.levelNum" :min="1" placeholder="请输入层级" />
        </el-form-item>
        <el-form-item label="位置" prop="positionNum">
          <el-input-number v-model="form.positionNum" :min="1" placeholder="请输入位置" />
        </el-form-item>
        <el-form-item label="容量" prop="capacity">
          <el-input-number v-model="form.capacity" :min="0" placeholder="请输入容量" />
        </el-form-item>
        <el-form-item label="已用容量" prop="usedCapacity">
          <el-input-number v-model="form.usedCapacity" :min="0" placeholder="请输入已用容量" />
        </el-form-item>
        <el-form-item label="最大承重(kg)" prop="maxWeight">
          <el-input-number v-model="form.maxWeight" :min="0" placeholder="请输入最大承重" />
        </el-form-item>
        <el-form-item label="当前重量(kg)" prop="currentWeight">
          <el-input-number v-model="form.currentWeight" :min="0" placeholder="请输入当前重量" />
        </el-form-item>
        <el-form-item label="占用状态" prop="isOccupied">
          <el-radio-group v-model="form.isOccupied">
            <el-radio
              v-for="dict in dict.type.location_is_occupied"
              :key="dict.value"
              :label="dict.value"
            >{{dict.label}}</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="状态" prop="isActive">
          <el-radio-group v-model="form.isActive">
            <el-radio
              v-for="dict in dict.type.sys_yes_no"
              :key="dict.value"
              :label="dict.value"
            >{{dict.label}}</el-radio>
          </el-radio-group>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>


  </div>
</template>

<script>
import { listLocation, getLocation, delLocation, addLocation, updateLocation } from "@/api/erp/location";
import { getAvailableWarehouses } from "@/api/erp/warehouse";
import { getShelvesByWarehouse } from "@/api/erp/shelf";
import BarcodeDisplay from "@/components/BarcodeDisplay";
import BarcodePrinter from "@/components/BarcodePrinter";

export default {
  name: "Location",
  components: {
    BarcodeDisplay,
    BarcodePrinter
  },
  dicts: ['location_type', 'location_is_occupied', 'sys_yes_no'],
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
      // 货位信息表格数据
      locationList: [],
      // 仓库列表
      warehouseList: [],
      // 货架列表
      shelfList: [],
      // 表单货架列表
      formShelfList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        locationCode: null,
        locationName: null,
        warehouseId: null,
        shelfId: null,
        locationType: null,
        isOccupied: null,
        isActive: null
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        locationCode: [
          { required: true, message: "货位编码不能为空", trigger: "blur" }
        ],
        locationName: [
          { required: true, message: "货位名称不能为空", trigger: "blur" }
        ],
        warehouseId: [
          { required: true, message: "所属仓库不能为空", trigger: "change" }
        ],
        shelfId: [
          { required: true, message: "所属货架不能为空", trigger: "change" }
        ],
        locationType: [
          { required: true, message: "货位类型不能为空", trigger: "change" }
        ]
      }
    };
  },
  created() {
    this.getList();
    this.getWarehouseList();
  },
  methods: {
    /** 查询货位信息列表 */
    getList() {
      this.loading = true;
      listLocation(this.queryParams).then(response => {
        this.locationList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    /** 获取仓库列表 */
    getWarehouseList() {
      getAvailableWarehouses().then(response => {
        this.warehouseList = response.data;
      });
    },
    /** 获取货架列表 */
    getShelfList(warehouseId) {
      if (warehouseId) {
        getShelvesByWarehouse(warehouseId).then(response => {
          this.shelfList = response.data;
        });
      } else {
        this.shelfList = [];
      }
    },
    /** 查询条件仓库变化 */
    handleWarehouseChange(warehouseId) {
      this.queryParams.shelfId = null;
      this.getShelfList(warehouseId);
    },
    /** 表单仓库变化 */
    handleFormWarehouseChange(warehouseId) {
      this.form.shelfId = null;
      if (warehouseId) {
        getShelvesByWarehouse(warehouseId).then(response => {
          this.formShelfList = response.data;
        });
      } else {
        this.formShelfList = [];
      }
    },
    // 取消按钮
    cancel() {
      this.open = false;
      this.reset();
    },
    // 表单重置
    reset() {
      this.form = {
        id: null,
        locationCode: null,
        locationName: null,
        shelfId: null,
        warehouseId: null,
        locationType: null,
        levelNum: null,
        positionNum: null,
        capacity: null,
        usedCapacity: null,
        maxWeight: null,
        currentWeight: null,
        isOccupied: '0',
        isActive: '1',
        delFlag: null,
        createBy: null,
        createTime: null,
        updateBy: null,
        updateTime: null
      };
      this.formShelfList = [];
      this.resetForm("form");
    },
    /** 搜索按钮操作 */
    handleQuery() {
      this.queryParams.pageNum = 1;
      this.getList();
    },
    /** 重置按钮操作 */
    resetQuery() {
      this.resetForm("queryForm");
      this.handleQuery();
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.id)
      this.single = selection.length!==1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加货位信息";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids
      getLocation(id).then(response => {
        this.form = response.data;
        // 根据仓库ID加载货架列表
        if (this.form.warehouseId) {
          this.handleFormWarehouseChange(this.form.warehouseId);
        }
        this.open = true;
        this.title = "修改货位信息";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != null) {
            updateLocation(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addLocation(this.form).then(response => {
              this.$modal.msgSuccess("新增成功");
              this.open = false;
              this.getList();
            });
          }
        }
      });
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const ids = row.id || this.ids;
      this.$modal.confirm('是否确认删除货位信息编号为"' + ids + '"的数据项？').then(function() {
        return delLocation(ids);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {});
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('erp/location/export', {
        ...this.queryParams
      }, `location_${new Date().getTime()}.xlsx`)
    },
    /** 处理货位状态变化 */
    handleStatusChange(row) {
      const text = row.isActive === '1' ? '启用' : '禁用';
      this.$modal.confirm('确认要' + text + '货位"' + row.locationName + '"吗？').then(() => {
        return updateLocation(row);
      }).then(() => {
        this.$modal.msgSuccess(text + '成功');
        this.getList();
      }).catch(() => {
        // 如果操作失败，恢复原状态
        row.isActive = row.isActive === '1' ? '0' : '1';
      });
    },
    /** 处理货位占用状态变化 */
    handleOccupiedChange(row) {
      const text = row.isOccupied === '1' ? '占用' : '释放';
      this.$modal.confirm('确认要' + text + '货位"' + row.locationName + '"吗？').then(() => {
        return updateLocation(row);
      }).then(() => {
        this.$modal.msgSuccess(text + '成功');
        this.getList();
      }).catch(() => {
        // 如果操作失败，恢复原状态
        row.isOccupied = row.isOccupied === '1' ? '0' : '1';
      });
    },

  }
};
</script>

<style scoped>
.el-table .barcode-display {
  margin-top: 5px;
}
</style>
