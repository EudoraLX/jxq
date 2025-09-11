<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="货架编码" prop="shelfCode">
        <el-input
          v-model="queryParams.shelfCode"
          placeholder="请输入货架编码"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="货架名称" prop="shelfName">
        <el-input
          v-model="queryParams.shelfName"
          placeholder="请输入货架名称"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="所属仓库" prop="warehouseId">
        <el-select v-model="queryParams.warehouseId" placeholder="请选择所属仓库" clearable>
          <el-option
            v-for="warehouse in warehouseList"
            :key="warehouse.id"
            :label="warehouse.warehouseName"
            :value="warehouse.id"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="货架类型" prop="shelfType">
        <el-select v-model="queryParams.shelfType" placeholder="请选择货架类型" clearable>
          <el-option
            v-for="dict in dict.type.shelf_type"
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
          v-hasPermi="['erp:shelf:add']"
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
          v-hasPermi="['erp:shelf:edit']"
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
          v-hasPermi="['erp:shelf:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['erp:shelf:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="shelfList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="货架编码" align="center" prop="shelfCode">
        <template slot-scope="scope">
          <div>
            <div>{{ scope.row.shelfCode }}</div>
            <barcode-display
              v-if="scope.row.shelfCode"
              :value="scope.row.shelfCode"
              :width="1.5"
              :height="30"
              format="CODE128"
            />
          </div>
        </template>
      </el-table-column>
      <el-table-column label="货架名称" align="center" prop="shelfName" />
      <el-table-column label="所属仓库" align="center" prop="warehouseName">
        <template slot-scope="scope">
          <span v-if="scope.row.warehouseName">{{ scope.row.warehouseName }} ({{ scope.row.warehouseCode }})</span>
          <span v-else>{{ scope.row.warehouseId }}</span>
        </template>
      </el-table-column>
      <el-table-column label="货架类型" align="center" prop="shelfType">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.shelf_type" :value="scope.row.shelfType"/>
        </template>
      </el-table-column>
      <el-table-column label="区域编码" align="center" prop="areaCode" />
      <el-table-column label="排号" align="center" prop="rowNum" />
      <el-table-column label="列号" align="center" prop="columnNum" />
      <el-table-column label="货架高度(米)" align="center" prop="height" />
      <el-table-column label="货架宽度(米)" align="center" prop="width" />
      <el-table-column label="货架深度(米)" align="center" prop="depth" />
      <el-table-column label="最大承重(kg)" align="center" prop="maxWeight" />
      <el-table-column label="是否启用" align="center" prop="isActive">
        <template slot-scope="scope">
          <el-switch
            v-model="scope.row.isActive"
            :active-value="'1'"
            :inactive-value="'0'"
            @change="handleStatusChange(scope.row)"
            v-hasPermi="['erp:shelf:edit']"
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
            v-hasPermi="['erp:shelf:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['erp:shelf:remove']"
          >删除</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-view"
            @click="handleViewLocations(scope.row)"
          >查看货位</el-button>
          <barcode-printer 
            :machine-data="{
              machineCode: scope.row.shelfCode,
              machineName: scope.row.shelfName,
              machineType: scope.row.shelfType,
              workCenterName: scope.row.warehouseName || '未设置仓库'
            }"
            :title="'货架标签'"
            :label-title="'货架标签'"
            :info-labels="{
              code: '货架编码',
              name: '货架名称',
              type: '货架类型',
              workCenter: '所属仓库'
            }"
            :machine-type-dict="dict.type.shelf_type"
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

    <!-- 添加或修改货架信息对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="600px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="货架编码" prop="shelfCode">
          <el-input v-model="form.shelfCode" placeholder="请输入货架编码" />
        </el-form-item>
        <el-form-item label="货架名称" prop="shelfName">
          <el-input v-model="form.shelfName" placeholder="请输入货架名称" />
        </el-form-item>
        <el-form-item label="所属仓库" prop="warehouseId">
          <el-select v-model="form.warehouseId" placeholder="请选择所属仓库">
            <el-option
              v-for="warehouse in warehouseList"
              :key="warehouse.id"
              :label="warehouse.warehouseName"
              :value="warehouse.id"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="货架类型" prop="shelfType">
          <el-select v-model="form.shelfType" placeholder="请选择货架类型">
            <el-option
              v-for="dict in dict.type.shelf_type"
              :key="dict.value"
              :label="dict.label"
              :value="dict.value"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="区域编码" prop="areaCode">
          <el-input v-model="form.areaCode" placeholder="请输入区域编码" />
        </el-form-item>
        <el-form-item label="行数" prop="rowNum">
          <el-input-number v-model="form.rowNum" :min="1" placeholder="请输入行数" />
        </el-form-item>
        <el-form-item label="列数" prop="columnNum">
          <el-input-number v-model="form.columnNum" :min="1" placeholder="请输入列数" />
        </el-form-item>
        <el-form-item label="高度(cm)" prop="height">
          <el-input-number v-model="form.height" :min="0" placeholder="请输入高度" />
        </el-form-item>
        <el-form-item label="宽度(cm)" prop="width">
          <el-input-number v-model="form.width" :min="0" placeholder="请输入宽度" />
        </el-form-item>
        <el-form-item label="深度(cm)" prop="depth">
          <el-input-number v-model="form.depth" :min="0" placeholder="请输入深度" />
        </el-form-item>
        <el-form-item label="最大承重(kg)" prop="maxWeight">
          <el-input-number v-model="form.maxWeight" :min="0" placeholder="请输入最大承重" />
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
import { listShelf, getShelf, delShelf, addShelf, updateShelf } from "@/api/erp/shelf";
import { getAvailableWarehouses } from "@/api/erp/warehouse";
import BarcodeDisplay from "@/components/BarcodeDisplay";
import BarcodePrinter from "@/components/BarcodePrinter";

export default {
  name: "Shelf",
  components: {
    BarcodeDisplay,
    BarcodePrinter
  },
  dicts: ['shelf_type', 'sys_yes_no'],
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
      // 货架信息表格数据
      shelfList: [],
      // 仓库列表
      warehouseList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        shelfCode: null,
        shelfName: null,
        warehouseId: null,
        shelfType: null,
        isActive: null
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        shelfCode: [
          { required: true, message: "货架编码不能为空", trigger: "blur" }
        ],
        shelfName: [
          { required: true, message: "货架名称不能为空", trigger: "blur" }
        ],
        warehouseId: [
          { required: true, message: "所属仓库不能为空", trigger: "change" }
        ],
        shelfType: [
          { required: true, message: "货架类型不能为空", trigger: "change" }
        ]
      }
    };
  },
  created() {
    this.getList();
    this.getWarehouseList();
  },
  methods: {
    /** 查询货架信息列表 */
    getList() {
      this.loading = true;
      listShelf(this.queryParams).then(response => {
        this.shelfList = response.rows;
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
    // 取消按钮
    cancel() {
      this.open = false;
      this.reset();
    },
    // 表单重置
    reset() {
      this.form = {
        id: null,
        shelfCode: null,
        shelfName: null,
        warehouseId: null,
        shelfType: null,
        areaCode: null,
        rowNum: null,
        columnNum: null,
        height: null,
        width: null,
        depth: null,
        maxWeight: null,
        isActive: '1',
        delFlag: null,
        createBy: null,
        createTime: null,
        updateBy: null,
        updateTime: null
      };
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
      this.title = "添加货架信息";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids
      getShelf(id).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改货架信息";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != null) {
            updateShelf(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addShelf(this.form).then(response => {
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
      this.$modal.confirm('是否确认删除货架信息编号为"' + ids + '"的数据项？').then(function() {
        return delShelf(ids);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {});
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('erp/shelf/export', {
        ...this.queryParams
      }, `shelf_${new Date().getTime()}.xlsx`)
    },
    /** 处理货架状态变化 */
    handleStatusChange(row) {
      const text = row.isActive === '1' ? '启用' : '禁用';
      this.$modal.confirm('确认要' + text + '货架"' + row.shelfName + '"吗？').then(() => {
        return updateShelf(row);
      }).then(() => {
        this.$modal.msgSuccess(text + '成功');
        this.getList();
      }).catch(() => {
        // 如果操作失败，恢复原状态
        row.isActive = row.isActive === '1' ? '0' : '1';
      });
    },
    /** 查看货位 */
    handleViewLocations(row) {
      // 这里可以跳转到货位管理页面，或者打开货位查看弹窗
      this.$router.push({
        path: '/erp/location',
        query: { shelfId: row.id }
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
