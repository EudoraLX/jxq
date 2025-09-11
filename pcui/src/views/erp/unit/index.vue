<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="单位编码" prop="unitCode">
        <el-input
          v-model="queryParams.unitCode"
          placeholder="请输入单位编码"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="单位名称" prop="unitName">
        <el-input
          v-model="queryParams.unitName"
          placeholder="请输入单位名称"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="单位符号" prop="unitSymbol">
        <el-input
          v-model="queryParams.unitSymbol"
          placeholder="请输入单位符号"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="是否基本单位" prop="isBaseUnit">
        <el-select v-model="queryParams.isBaseUnit" placeholder="请选择是否基本单位(0:否 1:是)" clearable>
          <el-option
            v-for="dict in dict.type.business_yes_no"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="状态" prop="status">
        <el-select v-model="queryParams.status" placeholder="请选择状态" clearable>
          <el-option
            v-for="dict in dict.type.sys_normal_disable"
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
          v-hasPermi="['erp:unit:add']"
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
          v-hasPermi="['erp:unit:edit']"
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
          v-hasPermi="['erp:unit:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['erp:unit:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="unitList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <!-- <el-table-column label="主键ID" align="center" prop="id" /> -->
      <el-table-column label="单位编码" align="center" prop="unitCode" />
      <el-table-column label="单位名称" align="center" prop="unitName" />
      <el-table-column label="单位符号" align="center" prop="unitSymbol" />
      <el-table-column label="是否基本单位" align="center" prop="isBaseUnit">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.business_yes_no" :value="scope.row.isBaseUnit"/>
        </template>
      </el-table-column>
      <el-table-column label="换算比例" align="center" prop="conversionRate" />
      <el-table-column label="基本单位" align="center" prop="baseUnitId">
        <template slot-scope="scope">
          <span v-if="scope.row.baseUnitId">{{ getBaseUnitSymbol(scope.row.baseUnitId) }}</span>
          <span v-else>-</span>
        </template>
      </el-table-column>
      <el-table-column label="状态" align="center" prop="status">
        <template slot-scope="scope">
          <el-switch v-model="scope.row.status" active-value="0" inactive-value="1" @change="handleStatusChange(scope.row)"></el-switch>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['erp:unit:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['erp:unit:remove']"
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

    <!-- 添加或修改计量单位对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="单位编码" prop="unitCode">
          <el-input v-model="form.unitCode" placeholder="请输入单位编码" />
        </el-form-item>
        <el-form-item label="单位名称" prop="unitName">
          <el-input v-model="form.unitName" placeholder="请输入单位名称" />
        </el-form-item>
        <el-form-item label="单位符号" prop="unitSymbol">
          <el-input v-model="form.unitSymbol" placeholder="请输入单位符号" />
        </el-form-item>
        <el-form-item label="是否基本单位" prop="isBaseUnit">
          <el-radio-group v-model="form.isBaseUnit">
            <el-radio
              v-for="dict in dict.type.business_yes_no"
              :key="dict.value"
              :label="dict.value"
            >{{dict.label}}</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="换算比例" prop="conversionRate">
          <el-input v-model="form.conversionRate" placeholder="请输入换算比例" />
        </el-form-item>
        <el-form-item label="基本单位" prop="baseUnitId">
          <el-input 
            v-model="baseUnitSymbol" 
            placeholder="请选择基本单位" 
            readonly
            @click.native="showBaseUnitSelect"
          >
            <el-button slot="append" icon="el-icon-search" @click="showBaseUnitSelect"></el-button>
          </el-input>
        </el-form-item>
        <el-form-item label="状态" prop="status">
          <el-switch v-model="form.status" active-value="0" inactive-value="1" active-text="正常" inactive-text="停用"></el-switch>
        </el-form-item>

      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
    
    <!-- 关联表选择组件 -->
    
    <!-- 基本单位选择弹窗 -->
    <base-unit-select ref="baseUnitSelect" @select="handleBaseUnitSelect" />
  </div>
</template>

<script>
import { listUnit, getUnit, delUnit, addUnit, updateUnit, changeUnitStatus } from "@/api/erp/unit"
import BaseUnitSelect from "@/components/BaseUnitSelect"

export default {
  name: "Unit",
  components: {
    BaseUnitSelect
  },
  dicts: ['business_yes_no', 'sys_normal_disable'],
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
      // 计量单位表格数据
      unitList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        unitCode: null,
        unitName: null,
        unitSymbol: null,
        isBaseUnit: null,
        status: null,
      },
      // 表单参数
      form: {},
      // 基本单位符号显示
      baseUnitSymbol: "",
      // 表单校验
      rules: {
        unitCode: [
          { required: true, message: "单位编码不能为空", trigger: "blur" }
        ],
        unitName: [
          { required: true, message: "单位名称不能为空", trigger: "blur" }
        ],
      }
    }
  },
  created() {
    this.getList()
  },
  methods: {
    /** 查询计量单位列表 */
    getList() {
      this.loading = true
      listUnit(this.queryParams).then(response => {
        this.unitList = response.rows
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
        unitCode: null,
        unitName: null,
        unitSymbol: null,
        isBaseUnit: null,
        conversionRate: null,
        baseUnitId: null,
        status: "0",
        createBy: null,
        createTime: null,
        updateBy: null,
        updateTime: null
      }
      this.baseUnitSymbol = ""
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
      this.title = "添加计量单位"
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset()
      const id = row.id || this.ids
      getUnit(id).then(response => {
        this.form = response.data
        // 设置基本单位符号显示
        if (this.form.baseUnitId) {
          this.baseUnitSymbol = this.getBaseUnitSymbol(this.form.baseUnitId)
        }
        this.open = true
        this.title = "修改计量单位"
      })
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != null) {
            updateUnit(this.form).then(response => {
              this.$modal.msgSuccess("修改成功")
              this.open = false
              this.getList()
            })
          } else {
            addUnit(this.form).then(response => {
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
      this.$modal.confirm('是否确认删除计量单位编号为"' + ids + '"的数据项？').then(function() {
        return delUnit(ids)
      }).then(() => {
        this.getList()
        this.$modal.msgSuccess("删除成功")
      }).catch(() => {})
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('erp/unit/export', {
        ...this.queryParams
      }, `unit_${new Date().getTime()}.xlsx`)
    },
    
    /** 计量单位状态修改 */
    handleStatusChange(row) {
      let text = row.status === "0" ? "启用" : "停用"
      this.$modal.confirm('确认要"' + text + '""' + row.unitName + '"计量单位吗？').then(function() {
        return changeUnitStatus(row.id, row.status)
      }).then(() => {
        this.$modal.msgSuccess(text + "成功")
      }).catch(function() {
        row.status = row.status === "0" ? "1" : "0"
      })
    },
    
    /** 显示基本单位选择弹窗 */
    showBaseUnitSelect() {
      this.$refs.baseUnitSelect.show()
    },
    
    /** 处理基本单位选择 */
    handleBaseUnitSelect(selectedUnit) {
      this.form.baseUnitId = selectedUnit.id
      this.baseUnitSymbol = selectedUnit.unitSymbol
    },
    
    /** 根据基本单位ID获取单位符号 */
    getBaseUnitSymbol(baseUnitId) {
      if (!baseUnitId) return '-'
      const baseUnit = this.unitList.find(unit => unit.id === baseUnitId)
      return baseUnit ? baseUnit.unitSymbol : baseUnitId
    },
    
    /** 查看关联数据 */
    handleViewRelatedData(tableName, relatedFields, value) {
      if (!value) {
        this.$modal.msgInfo('该字段没有关联数据')
        return
      }
      // 这里可以打开一个弹窗显示关联数据详情
      this.$modal.msgInfo('查看' + tableName + '表中字段' + relatedFields + '的值为' + value + '的数据')
    },
    
         /** 选择关联数据 */
     handleSelectRelatedData(tableName, relatedFields, fieldName, javaField) {
       // 动态导入关联表的select组件
       const componentName = this.getRelatedComponentName(tableName);
       if (componentName) {
         // 使用动态组件显示选择弹窗
         this.$refs[componentName + 'Select'].show((selectedData) => {
           // 设置选中值
           this.form[fieldName] = selectedData.id;
           // 设置显示值
           const displayValue = this.buildDisplayValue(selectedData, relatedFields);
           this.form[javaField + 'Display'] = displayValue;
         });
       } else {
         this.$modal.msgError('未找到关联表的select组件：' + tableName);
       }
     },
     
     /** 获取关联组件的名称 */
     getRelatedComponentName(tableName) {
       // 根据表名生成组件名称，例如：erp_material_category -> MaterialCategory
       const parts = tableName.split('_');
       let componentName = '';
       for (let i = 1; i < parts.length; i++) {
         componentName += parts[i].charAt(0).toUpperCase() + parts[i].slice(1);
       }
       return componentName;
     },
     
     /** 构建显示值 */
     buildDisplayValue(selectedData, relatedFields) {
       if (!relatedFields) return '';
       const fields = relatedFields.split(',');
       const values = [];
       fields.forEach(field => {
         const trimmedField = field.trim();
         if (selectedData[trimmedField]) {
           values.push(selectedData[trimmedField]);
         }
       });
       return values.join(' - ');
     }
  }
}
</script>
 