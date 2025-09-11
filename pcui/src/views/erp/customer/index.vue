<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="客户编码" prop="customerCode">
        <el-input
          v-model="queryParams.customerCode"
          placeholder="请输入客户编码"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="客户名称" prop="customerName">
        <el-input
          v-model="queryParams.customerName"
          placeholder="请输入客户名称"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="客户简称" prop="customerShortName">
        <el-input
          v-model="queryParams.customerShortName"
          placeholder="请输入客户简称"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="客户类型" prop="customerType">
        <el-select v-model="queryParams.customerType" placeholder="请选择客户类型" clearable>
          <el-option
            v-for="dict in dict.type.erp_customer_type"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="所属行业" prop="industry">
        <el-select v-model="queryParams.industry" placeholder="请选择所属行业" clearable>
          <el-option
            v-for="dict in dict.type.erp_industry_type"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="联系人" prop="contactPerson">
        <el-input
          v-model="queryParams.contactPerson"
          placeholder="请输入联系人"
          clearable
          @keyup.enter.native="handleQuery"
        />
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
          v-hasPermi="['erp:customer:add']"
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
          v-hasPermi="['erp:customer:edit']"
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
          v-hasPermi="['erp:customer:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['erp:customer:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="customerList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <!-- <el-table-column label="客户ID" align="center" prop="id" /> -->
      <el-table-column label="客户编码" align="center" prop="customerCode" />
      <el-table-column label="客户名称" align="center" prop="customerName" />
      <el-table-column label="客户简称" align="center" prop="customerShortName" />
      <el-table-column label="客户类型" align="center" prop="customerType">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.erp_customer_type" :value="scope.row.customerType"/>
        </template>
      </el-table-column>
      <el-table-column label="所属行业" align="center" prop="industry">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.erp_industry_type" :value="scope.row.industry"/>
        </template>
      </el-table-column>
      <el-table-column label="成立日期" align="center" prop="establishmentDate" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.establishmentDate, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="联系人" align="center" prop="contactPerson" />
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
            v-hasPermi="['erp:customer:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['erp:customer:remove']"
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

    <!-- 添加或修改客户对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="90%" append-to-body>
      <el-tabs v-model="activeTab" type="border-card">
        <el-tab-pane label="基本信息" name="basic">
          <el-form ref="form" :model="form" :rules="rules" label-width="120px" style="padding: 20px;">
            <el-row :gutter="20">
              <el-col :span="12">
                <el-form-item label="客户编码" prop="customerCode">
                  <el-input v-model="form.customerCode" placeholder="请输入客户编码" />
                </el-form-item>
              </el-col>
              <el-col :span="12">
                <el-form-item label="客户名称" prop="customerName">
                  <el-input v-model="form.customerName" placeholder="请输入客户名称" />
                </el-form-item>
              </el-col>
            </el-row>

            <el-row :gutter="20">
              <el-col :span="12">
                <el-form-item label="客户简称" prop="customerShortName">
                  <el-input v-model="form.customerShortName" placeholder="请输入客户简称" />
                </el-form-item>
              </el-col>
              <el-col :span="12">
                <el-form-item label="客户类型" prop="customerType">
                  <el-select v-model="form.customerType" placeholder="请选择客户类型" style="width:100%">
                    <el-option v-for="dict in dict.type.erp_customer_type" :key="dict.value" :label="dict.label" :value="dict.value" />
                  </el-select>
                </el-form-item>
              </el-col>
            </el-row>

            <el-row :gutter="20">
              <el-col :span="12">
                <el-form-item label="所属行业" prop="industry">
                  <el-select v-model="form.industry" placeholder="请选择所属行业" style="width:100%">
                    <el-option v-for="dict in dict.type.erp_industry_type" :key="dict.value" :label="dict.label" :value="dict.value" />
                  </el-select>
                </el-form-item>
              </el-col>
              <el-col :span="12">
                <el-form-item label="成立日期" prop="establishmentDate">
                  <el-date-picker clearable v-model="form.establishmentDate" type="date" value-format="yyyy-MM-dd" placeholder="请选择成立日期" style="width:100%" />
                </el-form-item>
              </el-col>
            </el-row>

            <el-row :gutter="20">
              <el-col :span="24">
                <el-form-item label="经营范围" prop="businessScope">
                  <el-input v-model="form.businessScope" type="textarea" placeholder="请输入经营范围" />
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

            <el-row :gutter="20">
              <el-col :span="12">
                <el-form-item label="信用额度" prop="creditLimit">
                  <el-input v-model="form.creditLimit" placeholder="请输入信用额度" />
                </el-form-item>
              </el-col>
              <el-col :span="12">
                <el-form-item label="是否启用" prop="isActive">
                  <el-radio-group v-model="form.isActive">
                    <el-radio v-for="dict in dict.type.business_active" :key="dict.value" :label="dict.value">{{dict.label}}</el-radio>
                  </el-radio-group>
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
  </div>
</template>

<script>
import { listCustomer, getCustomer, delCustomer, addCustomer, updateCustomer } from "@/api/erp/customer"

export default {
  name: "Customer",
  dicts: ['business_active', 'erp_customer_type', 'erp_credit_rating', 'erp_industry_type'],
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
      // 客户表格数据
      customerList: [],
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
        customerCode: null,
        customerName: null,
        customerShortName: null,
        customerType: null,
        industry: null,
        contactPerson: null,
        creditRating: null,
        isActive: null,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        customerCode: [
          { required: true, message: "客户编码不能为空", trigger: "blur" }
        ],
        customerName: [
          { required: true, message: "客户名称不能为空", trigger: "blur" }
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
    /** 查询客户列表 */
    getList() {
      this.loading = true
      listCustomer(this.queryParams).then(response => {
        this.customerList = response.rows
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
        customerCode: null,
        customerName: null,
        customerShortName: null,
        customerType: null,
        industry: null,
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
        creditLimit: null,
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
      this.title = "添加客户"
      this.activeTab = "basic"
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset()
      const id = row.id || this.ids
      getCustomer(id).then(response => {
        this.form = response.data
        this.open = true
        this.title = "修改客户"
        this.activeTab = "basic"
      })
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != null) {
            updateCustomer(this.form).then(response => {
              this.$modal.msgSuccess("修改成功")
              this.open = false
              this.getList()
            })
          } else {
            addCustomer(this.form).then(response => {
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
      this.$modal.confirm('是否确认删除客户编号为"' + ids + '"的数据项？').then(function() {
        return delCustomer(ids)
      }).then(() => {
        this.getList()
        this.$modal.msgSuccess("删除成功")
      }).catch(() => {})
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('erp/customer/export', {
        ...this.queryParams
      }, `customer_${new Date().getTime()}.xlsx`)
    }
  }
}
</script>
