<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="入库单号" prop="inboundNo">
        <el-input
          v-model="queryParams.inboundNo"
          placeholder="请输入入库单号"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="物料编码" prop="materialCode">
        <el-input
          v-model="queryParams.materialCode"
          placeholder="请输入物料编码"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="物料名称" prop="materialName">
        <el-input
          v-model="queryParams.materialName"
          placeholder="请输入物料名称"
          clearable
          @keyup.enter.native="handleQuery"
        />
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
          v-hasPermi="['inventory:inbound:add']"
        >新增明细</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          plain
          icon="el-icon-edit"
          size="mini"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['inventory:inbound:edit']"
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
          v-hasPermi="['inventory:inbound:remove']"
        >删除</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="detailList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="物料编码" align="center" prop="materialCode" width="120" />
      <el-table-column label="物料名称" align="center" prop="materialName" width="200" />
      <el-table-column label="物料规格" align="center" prop="materialSpec" width="150" />
      <el-table-column label="物料型号" align="center" prop="materialModel" width="150" />
      <el-table-column label="单位" align="center" prop="unitName" width="80" />
      <el-table-column label="计划数量" align="center" prop="plannedQuantity" width="100">
        <template slot-scope="scope">
          <el-input-number
            v-model="scope.row.plannedQuantity"
            :min="0"
            :precision="4"
            :controls="false"
            :step="0.1"
            size="mini"
            style="width: 100%"
            @change="handleQuantityChange(scope.row)"
          />
        </template>
      </el-table-column>
      <el-table-column label="实际数量" align="center" prop="actualQuantity" width="100">
        <template slot-scope="scope">
          <el-input-number
            v-model="scope.row.actualQuantity"
            :min="0"
            :precision="4"
            :controls="false"
            :step="0.1"
            size="mini"
            style="width: 100%"
            @change="handleQuantityChange(scope.row)"
          />
        </template>
      </el-table-column>
      <el-table-column label="单价" align="center" prop="unitPrice" width="100">
        <template slot-scope="scope">
          <el-input-number
            v-model="scope.row.unitPrice"
            :min="0"
            :precision="2"
            :controls="false"
            :step="0.01"
            size="mini"
            style="width: 100%"
            @change="handleQuantityChange(scope.row)"
          />
        </template>
      </el-table-column>
      <el-table-column label="总金额" align="center" prop="totalAmount" width="100">
        <template slot-scope="scope">
          <span>{{ parseFloat(scope.row.totalAmount || 0).toFixed(2) }}</span>
        </template>
      </el-table-column>
      <el-table-column label="批次号" align="center" prop="batchNo" width="120" />
      <el-table-column label="生产日期" align="center" prop="productionDate" width="120">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.productionDate, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="到期日期" align="center" prop="expiryDate" width="120">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.expiryDate, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="质量状态" align="center" prop="qualityStatus" width="100">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.quality_status" :value="scope.row.qualityStatus"/>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width" width="200">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['inventory:inbound:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['inventory:inbound:remove']"
          >删除</el-button>
        </template>
      </el-table-column>
    </el-table>

    <!-- 新增/修改明细对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="800px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="100px">
        <el-row>
          <el-col :span="12">
            <el-form-item label="物料" prop="materialId">
              <el-input
                v-model="materialDisplayText"
                placeholder="请选择物料"
                readonly
                @click="showMaterialDialog = true"
              >
                <el-button slot="append" icon="el-icon-search" @click="showMaterialDialog = true">选择</el-button>
              </el-input>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="单位" prop="unitName">
              <el-input v-model="form.unitName" placeholder="单位名称" readonly />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="计划数量" prop="plannedQuantity">
              <el-input-number
                v-model="form.plannedQuantity"
                :min="0"
                :precision="4"
                :controls="false"
                :step="0.1"
                style="width: 100%"
                @change="handleFormQuantityChange"
              />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="实际数量" prop="actualQuantity">
              <el-input-number
                v-model="form.actualQuantity"
                :min="0"
                :precision="4"
                :controls="false"
                :step="0.1"
                style="width: 100%"
                @change="handleFormQuantityChange"
              />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="单价" prop="unitPrice">
              <el-input-number
                v-model="form.unitPrice"
                :min="0"
                :precision="2"
                :controls="false"
                :step="0.01"
                style="width: 100%"
                @change="handleFormQuantityChange"
              />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="总金额" prop="totalAmount">
              <el-input v-model="form.totalAmount" placeholder="总金额" readonly />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="批次号" prop="batchNo">
              <el-input v-model="form.batchNo" placeholder="请输入批次号" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="批号" prop="lotNo">
              <el-input v-model="form.lotNo" placeholder="请输入批号" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="生产日期" prop="productionDate">
              <el-date-picker
                v-model="form.productionDate"
                type="date"
                placeholder="选择生产日期"
                style="width: 100%"
              />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="到期日期" prop="expiryDate">
              <el-date-picker
                v-model="form.expiryDate"
                type="date"
                placeholder="选择到期日期"
                style="width: 100%"
              />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="质量状态" prop="qualityStatus">
              <el-select v-model="form.qualityStatus" placeholder="请选择质量状态">
                <el-option
                  v-for="dict in dict.type.quality_status"
                  :key="dict.value"
                  :label="dict.label"
                  :value="dict.value"
                />
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="质量等级" prop="qualityLevel">
              <el-select v-model="form.qualityLevel" placeholder="请选择质量等级">
                <el-option
                  v-for="dict in dict.type.quality_level"
                  :key="dict.value"
                  :label="dict.label"
                  :value="dict.value"
                />
              </el-select>
            </el-form-item>
          </el-col>
        </el-row>
        <el-form-item label="备注" prop="remark">
          <el-input v-model="form.remark" type="textarea" placeholder="请输入备注" />
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>

    <!-- 物料选择对话框 -->
    <MaterialSelectDialog
      :visible.sync="showMaterialDialog"
      @confirm="handleMaterialSelect"
    />
  </div>
</template>

<script>
import { listInboundOrderDetail, getInboundOrderDetail, delInboundOrderDetail, addInboundOrderDetail, updateInboundOrderDetail } from "@/api/inventory/inboundDetail"
import MaterialSelectDialog from "@/components/MaterialSelectDialog"

export default {
  name: "InboundOrderDetail",
  components: {
    MaterialSelectDialog
  },
  dicts: ['quality_status', 'quality_level'],
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
      // 明细表格数据
      detailList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 是否显示物料选择对话框
      showMaterialDialog: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        inboundId: null,
        inboundNo: null,
        materialCode: null,
        materialName: null
      },
      // 表单参数
      form: {},
      // 物料显示文本
      materialDisplayText: "",
      // 表单校验
      rules: {
        materialId: [
          { required: true, message: "物料不能为空", trigger: "change" }
        ],
        plannedQuantity: [
          { required: true, message: "计划数量不能为空", trigger: "blur" }
        ],
        unitPrice: [
          { required: true, message: "单价不能为空", trigger: "blur" }
        ]
      }
    };
  },
  created() {
    this.getList();
  },
  methods: {
    /** 查询明细列表 */
    getList() {
      this.loading = true;
      listInboundOrderDetail(this.queryParams).then(response => {
        this.detailList = response.rows;
        this.total = response.total;
        this.loading = false;
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
        inboundId: null,
        inboundNo: null,
        materialId: null,
        materialCode: null,
        materialName: null,
        materialSpec: null,
        materialModel: null,
        unitId: null,
        unitCode: null,
        unitName: null,
        plannedQuantity: null,
        actualQuantity: null,
        unitPrice: null,
        totalAmount: null,
        batchNo: null,
        lotNo: null,
        serialNo: null,
        productionDate: null,
        expiryDate: null,
        qualityStatus: "1",
        qualityLevel: "1",
        remark: null
      };
      this.materialDisplayText = "";
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
      this.title = "添加入库明细";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids
      getInboundOrderDetail(id).then(response => {
        this.form = response.data;
        this.materialDisplayText = `${response.data.materialCode} - ${response.data.materialName}`;
        this.open = true;
        this.title = "修改入库明细";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != null) {
            updateInboundOrderDetail(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addInboundOrderDetail(this.form).then(response => {
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
      this.$modal.confirm('是否确认删除入库明细编号为"' + ids + '"的数据项？').then(function() {
        return delInboundOrderDetail(ids);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {});
    },
    /** 物料选择确认 */
    handleMaterialSelect(material) {
      this.form.materialId = material.materialId;
      this.form.materialCode = material.materialCode;
      this.form.materialName = material.materialName;
      this.form.materialSpec = material.materialSpec;
      this.form.materialModel = material.materialModel;
      this.form.unitId = material.unitId;
      this.form.unitCode = material.unitCode;
      this.form.unitName = material.unitName;
      this.form.unitPrice = material.unitPrice;
      this.materialDisplayText = `${material.materialCode} - ${material.materialName}`;
    },
    /** 数量变化处理 */
    handleQuantityChange(row) {
      if (row.actualQuantity && row.unitPrice) {
        row.totalAmount = (parseFloat(row.actualQuantity) * parseFloat(row.unitPrice)).toFixed(2);
      } else {
        row.totalAmount = 0;
      }
    },
    /** 表单数量变化处理 */
    handleFormQuantityChange() {
      if (this.form.actualQuantity && this.form.unitPrice) {
        this.form.totalAmount = (parseFloat(this.form.actualQuantity) * parseFloat(this.form.unitPrice)).toFixed(2);
      } else {
        this.form.totalAmount = 0;
      }
    }
  }
};
</script>

<style scoped>
.el-input-number {
  width: 100%;
}
</style>
