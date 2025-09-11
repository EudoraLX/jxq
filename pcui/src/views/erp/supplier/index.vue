<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="供应商编码" prop="supplierCode">
        <el-input
          v-model="queryParams.supplierCode"
          placeholder="请输入供应商编码"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="供应商名称" prop="supplierName">
        <el-input
          v-model="queryParams.supplierName"
          placeholder="请输入供应商名称"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="供应商简称" prop="supplierShortName">
        <el-input
          v-model="queryParams.supplierShortName"
          placeholder="请输入供应商简称"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="供应商类型" prop="supplierType">
        <el-select v-model="queryParams.supplierType" placeholder="请选择供应商类型" clearable>
          <el-option
            v-for="dict in dict.type.erp_supplier_type"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="信用等级" prop="creditRating">
        <el-select v-model="queryParams.creditRating" placeholder="请选择信用等级" clearable>
          <el-option
            v-for="dict in dict.type.erp_credit_rating"
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
          v-hasPermi="['erp:supplier:add']"
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
          v-hasPermi="['erp:supplier:edit']"
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
          v-hasPermi="['erp:supplier:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['erp:supplier:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="supplierList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <!-- <el-table-column label="供应商ID" align="center" prop="id" /> -->
      <el-table-column label="供应商编码" align="center" prop="supplierCode" />
      <el-table-column label="供应商名称" align="center" prop="supplierName" />
      <el-table-column label="供应商简称" align="center" prop="supplierShortName" />
      <el-table-column label="供应商类型" align="center" prop="supplierType">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.erp_supplier_type" :value="scope.row.supplierType"/>
        </template>
      </el-table-column>
      <el-table-column label="信用等级" align="center" prop="creditRating">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.erp_credit_rating" :value="scope.row.creditRating"/>
        </template>
      </el-table-column>
      <el-table-column label="是否启用" align="center" prop="isActive">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.business_active" :value="scope.row.isActive"/>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['erp:supplier:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['erp:supplier:remove']"
          >删除</el-button>
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

    <!-- 添加或修改供应商对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="90%" append-to-body>
      <el-tabs v-model="activeTab" type="border-card">
        <el-tab-pane label="基本信息" name="basic">
          <el-form ref="form" :model="form" :rules="rules" label-width="120px" style="padding: 20px;">
            <el-row :gutter="20">
              <el-col :span="12">
                <el-form-item label="供应商编码" prop="supplierCode">
                  <el-input v-model="form.supplierCode" placeholder="请输入供应商编码" />
                </el-form-item>
              </el-col>
              <el-col :span="12">
                <el-form-item label="供应商名称" prop="supplierName">
                  <el-input v-model="form.supplierName" placeholder="请输入供应商名称" />
                </el-form-item>
              </el-col>
            </el-row>

            <el-row :gutter="20">
              <el-col :span="12">
                <el-form-item label="供应商简称" prop="supplierShortName">
                  <el-input v-model="form.supplierShortName" placeholder="请输入供应商简称" />
                </el-form-item>
              </el-col>
              <el-col :span="12">
                <el-form-item label="供应商类型" prop="supplierType">
                  <el-select v-model="form.supplierType" placeholder="请选择供应商类型" style="width:100%">
                    <el-option v-for="dict in dict.type.erp_supplier_type" :key="dict.value" :label="dict.label" :value="dict.value" />
                  </el-select>
                </el-form-item>
              </el-col>
            </el-row>

            <el-row :gutter="20">
              <el-col :span="12">
                <el-form-item label="成立日期" prop="establishmentDate">
                  <el-date-picker clearable v-model="form.establishmentDate" type="date" value-format="yyyy-MM-dd" placeholder="请选择成立日期" style="width:100%" />
                </el-form-item>
              </el-col>
              <el-col :span="12">
                <el-form-item label="经营范围" prop="businessScope">
                  <el-input v-model="form.businessScope" placeholder="请输入经营范围" />
                </el-form-item>
              </el-col>
            </el-row>
          </el-form>
        </el-tab-pane>

        <el-tab-pane label="联系人与地址" name="contact">
          <el-form :model="form" label-width="120px" style="padding: 20px;">
            <el-row :gutter="20">
              <el-col :span="12">
                <el-form-item label="联系人" prop="contactPerson">
                  <el-input v-model="form.contactPerson" placeholder="请输入联系人" />
                </el-form-item>
              </el-col>
              <el-col :span="12">
                <el-form-item label="联系电话" prop="contactPhone">
                  <el-input v-model="form.contactPhone" placeholder="请输入联系电话" />
                </el-form-item>
              </el-col>
            </el-row>

            <el-row :gutter="20">
              <el-col :span="12">
                <el-form-item label="联系邮箱" prop="contactEmail">
                  <el-input v-model="form.contactEmail" placeholder="请输入联系邮箱" />
                </el-form-item>
              </el-col>
              <el-col :span="12">
                <el-form-item label="邮政编码" prop="postalCode">
                  <el-input v-model="form.postalCode" placeholder="请输入邮政编码" />
                </el-form-item>
              </el-col>
            </el-row>

            <el-row :gutter="20">
              <el-col :span="24">
                <el-form-item label="地址" prop="address">
                  <el-input v-model="form.address" type="textarea" placeholder="请输入地址" />
                </el-form-item>
              </el-col>
            </el-row>
          </el-form>
        </el-tab-pane>

        <el-tab-pane label="资质与账户" name="license">
          <el-form :model="form" label-width="120px" style="padding: 20px;">
            <el-row :gutter="20">
              <el-col :span="12">
                <el-form-item label="营业执照号" prop="businessLicense">
                  <el-input v-model="form.businessLicense" placeholder="请输入营业执照号" />
                </el-form-item>
              </el-col>
              <el-col :span="12">
                <el-form-item label="税务登记号" prop="taxRegistrationNumber">
                  <el-input v-model="form.taxRegistrationNumber" placeholder="请输入税务登记号" />
                </el-form-item>
              </el-col>
            </el-row>

            <el-row :gutter="20">
              <el-col :span="12">
                <el-form-item label="法定代表人" prop="legalRepresentative">
                  <el-input v-model="form.legalRepresentative" placeholder="请输入法定代表人" />
                </el-form-item>
              </el-col>
              <el-col :span="12">
                <el-form-item label="注册资本" prop="registeredCapital">
                  <el-input v-model="form.registeredCapital" placeholder="请输入注册资本" />
                </el-form-item>
              </el-col>
            </el-row>

            <el-row :gutter="20">
              <el-col :span="12">
                <el-form-item label="开户行" prop="bankName">
                  <el-input v-model="form.bankName" placeholder="请输入开户行" />
                </el-form-item>
              </el-col>
              <el-col :span="12">
                <el-form-item label="银行账号" prop="bankAccount">
                  <el-input v-model="form.bankAccount" placeholder="请输入银行账号" />
                </el-form-item>
              </el-col>
            </el-row>

            <el-row :gutter="20">
              <el-col :span="12">
                <el-form-item label="账户名称" prop="accountName">
                  <el-input v-model="form.accountName" placeholder="请输入账户名称" />
                </el-form-item>
              </el-col>
              <el-col :span="12">
                <el-form-item label="信用等级" prop="creditRating">
                  <el-select v-model="form.creditRating" placeholder="请选择信用等级" style="width:100%">
                    <el-option v-for="dict in dict.type.erp_credit_rating" :key="dict.value" :label="dict.label" :value="dict.value" />
                  </el-select>
                </el-form-item>
              </el-col>
            </el-row>
          </el-form>
        </el-tab-pane>

        <el-tab-pane label="其他" name="other">
          <el-form :model="form" label-width="120px" style="padding: 20px;">
            <el-row :gutter="20">
              <el-col :span="12">
                <el-form-item label="付款条件" prop="paymentTerms">
                  <el-input v-model="form.paymentTerms" placeholder="请输入付款条件" />
                </el-form-item>
              </el-col>
              <el-col :span="12">
                <el-form-item label="交货条件" prop="deliveryTerms">
                  <el-input v-model="form.deliveryTerms" placeholder="请输入交货条件" />
                </el-form-item>
              </el-col>
            </el-row>

            <el-row :gutter="20">
              <el-col :span="12">
                <el-form-item label="质量评级" prop="qualityRating">
                  <el-input v-model="form.qualityRating" placeholder="请输入质量评级" />
                </el-form-item>
              </el-col>
              <el-col :span="12">
                <el-form-item label="交付评级" prop="deliveryRating">
                  <el-input v-model="form.deliveryRating" placeholder="请输入交付评级" />
                </el-form-item>
              </el-col>
            </el-row>

            <el-row :gutter="20">
              <el-col :span="12">
                <el-form-item label="服务评级" prop="serviceRating">
                  <el-input v-model="form.serviceRating" placeholder="请输入服务评级" />
                </el-form-item>
              </el-col>
              <el-col :span="12">
                <el-form-item label="综合评级" prop="overallRating">
                  <el-input v-model="form.overallRating" placeholder="请输入综合评级" />
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
  </div>
</template>

<script>
import { listSupplier, getSupplier, delSupplier, addSupplier, updateSupplier } from "@/api/erp/supplier"

export default {
  name: "Supplier",
  dicts: ['business_active', 'erp_credit_rating', 'erp_supplier_type'],
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
      // 供应商表格数据
      supplierList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 当前激活的Tab
      activeTab: "basic",
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        supplierCode: null,
        supplierName: null,
        supplierShortName: null,
        supplierType: null,
        creditRating: null,
        isActive: null,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        supplierCode: [
          { required: true, message: "供应商编码不能为空", trigger: "blur" }
        ],
        supplierName: [
          { required: true, message: "供应商名称不能为空", trigger: "blur" }
        ],
        isActive: [
          { required: true, message: "是否启用不能为空", trigger: "change" }
        ],
      }
    }
  },
  created() {
    this.getList()
  },
  methods: {
    /** 查询供应商列表 */
    getList() {
      this.loading = true
      listSupplier(this.queryParams).then(response => {
        this.supplierList = response.rows
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
        supplierCode: null,
        supplierName: null,
        supplierShortName: null,
        supplierType: null,
        businessLicense: null,
        taxRegistrationNumber: null,
        legalRepresentative: null,
        registeredCapital: null,
        establishmentDate: null,
        businessScope: null,
        contactPerson: null,
        contactPhone: null,
        contactEmail: null,
        address: null,
        postalCode: null,
        bankName: null,
        bankAccount: null,
        accountName: null,
        creditRating: null,
        paymentTerms: null,
        deliveryTerms: null,
        qualityRating: null,
        deliveryRating: null,
        serviceRating: null,
        overallRating: null,
        isActive: null,
        remark: null,
        createBy: null,
        createTime: null,
        updateBy: null,
        updateTime: null
      }
      this.activeTab = "basic"
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
      this.title = "添加供应商"
      this.activeTab = "basic"
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset()
      const id = row.id || this.ids
      getSupplier(id).then(response => {
        this.form = response.data
        this.open = true
        this.title = "修改供应商"
        this.activeTab = "basic"
      })
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != null) {
            updateSupplier(this.form).then(response => {
              this.$modal.msgSuccess("修改成功")
              this.open = false
              this.getList()
            })
          } else {
            addSupplier(this.form).then(response => {
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
      this.$modal.confirm('是否确认删除供应商编号为"' + ids + '"的数据项？').then(function() {
        return delSupplier(ids)
      }).then(() => {
        this.getList()
        this.$modal.msgSuccess("删除成功")
      }).catch(() => {})
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('erp/supplier/export', {
        ...this.queryParams
      }, `supplier_${new Date().getTime()}.xlsx`)
    }
  }
}
</script>
