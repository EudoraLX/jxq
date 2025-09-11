<template>
  <el-dialog :title="title" :visible.sync="visible" width="1000px" append-to-body>
    <!-- 上半部分：仓库信息表单 -->
    <div class="warehouse-form-section">
      <el-tabs v-model="activeTab" type="border-card">
        <!-- 基本信息Tab -->
        <el-tab-pane label="基本信息" name="basic">
          <el-form ref="warehouseRef" :model="form" :rules="rules" label-width="100px">
            <el-row>
              <el-col :span="12">
                <el-form-item label="仓库编码" prop="warehouseCode">
                  <el-input v-model="form.warehouseCode" placeholder="请输入仓库编码" />
                </el-form-item>
              </el-col>
              <el-col :span="12">
                <el-form-item label="状态" prop="isActive">
                  <el-radio-group v-model="form.isActive">
                    <el-radio
                      v-for="dict in dict.type.sys_yes_no"
                      :key="dict.value"
                      :label="dict.value"
                    >{{dict.label}}</el-radio>
                  </el-radio-group>
                </el-form-item>
              </el-col>
            </el-row>
            <el-row>
              <el-col :span="12">
                <el-form-item label="仓库名称" prop="warehouseName">
                  <el-input v-model="form.warehouseName" placeholder="请输入仓库名称" />
                </el-form-item>
              </el-col>
              <el-col :span="12">
                <el-form-item label="仓库类型" prop="warehouseType">
                  <el-select v-model="form.warehouseType" placeholder="请选择仓库类型" style="width: 100%">
                    <el-option
                      v-for="dict in dict.type.warehouse_type"
                      :key="dict.value"
                      :label="dict.label"
                      :value="dict.value"
                    ></el-option>
                  </el-select>
                </el-form-item>
              </el-col>
            </el-row>
            <el-row>
              <el-col :span="12">
                <el-form-item label="负责人" prop="manager">
                  <div class="manager-input-group">
                    <el-input v-model="form.manager" placeholder="请输入负责人" />
                  </div>
                </el-form-item>
              </el-col>
              <el-col :span="12">
                <el-form-item label="联系电话" prop="phone">
                  <el-input v-model="form.phone" placeholder="请输入联系电话" />
                </el-form-item>
              </el-col>
            </el-row>
          </el-form>
        </el-tab-pane>
        
        <!-- 备注Tab -->
        <el-tab-pane label="备注" name="remark">
          <el-form ref="warehouseRef" :model="form" :rules="rules" label-width="100px">
            <el-form-item label="仓库地址" prop="address">
              <el-input v-model="form.address" type="textarea" :rows="3" placeholder="请输入仓库地址" />
            </el-form-item>
            <el-form-item label="备注" prop="remark">
              <el-input v-model="form.remark" type="textarea" :rows="4" placeholder="请输入备注内容" />
            </el-form-item>
          </el-form>
        </el-tab-pane>
      </el-tabs>
      
      <!-- 添加货架按钮 -->
      <div class="add-shelf-section">
        <el-button type="success" icon="el-icon-plus" @click="openShelfDialog">添加货架</el-button>
      </div>
    </div>

    <!-- 下半部分：货架信息表格 -->
    <div class="shelf-table-section">
      <h3>货架信息</h3>
      <el-table 
        v-loading="shelfLoading" 
        :data="shelfList" 
        stripe 
        border 
        class="shelf-table"
      >
        <el-table-column label="货架编码" align="center" prop="shelfCode" width="120" />
        <el-table-column label="货架名称" align="center" prop="shelfName" width="150" />
        <el-table-column label="货架类型" align="center" prop="shelfType" width="120">
          <template slot-scope="scope">
            <dict-tag :options="dict.type.shelf_type" :value="scope.row.shelfType"/>
          </template>
        </el-table-column>
        <el-table-column label="区域编码" align="center" prop="areaCode" width="100" />
        <el-table-column label="排号" align="center" prop="rowNum" width="80" />
        <el-table-column label="列号" align="center" prop="columnNum" width="80" />
        <el-table-column label="高度(米)" align="center" prop="height" width="100" />
        <el-table-column label="宽度(米)" align="center" prop="width" width="100" />
        <el-table-column label="深度(米)" align="center" prop="depth" width="100" />
        <el-table-column label="最大承重(kg)" align="center" prop="maxWeight" width="120" />
        <el-table-column label="状态" align="center" prop="isActive" width="80">
          <template slot-scope="scope">
            <dict-tag :options="dict.type.sys_yes_no" :value="scope.row.isActive"/>
          </template>
        </el-table-column>
        <el-table-column label="操作" align="center" class-name="small-padding fixed-width" width="200">
          <template slot-scope="scope">
            <el-button
              size="mini"
              type="text"
              icon="el-icon-edit"
              @click="handleEditShelf(scope.row)"
            >编辑</el-button>
            <el-button
              size="mini"
              type="text"
              icon="el-icon-delete"
              @click="handleDeleteShelf(scope.row)"
            >删除</el-button>
          </template>
        </el-table-column>
      </el-table>
    </div>

    <!-- 货架表单弹窗 -->
    <shelf-form-dialog
      :value="shelfFormVisible"
      @input="shelfFormVisible = $event"
      :title="shelfFormTitle"
      :shelf-data="currentShelf"
      :warehouse-id="form.id"
      :warehouse-data="form"
      @success="handleShelfSuccess"
    />

    <template slot="footer">
      <div class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </template>
  </el-dialog>
</template>

<script>
import { addWarehouse, updateWarehouse } from "@/api/erp/warehouse"
import { getShelvesByWarehouse, addShelf, updateShelf, delShelf } from "@/api/erp/shelf"
import ShelfFormDialog from "./ShelfFormDialog.vue"

export default {
  name: "WarehouseFormDialog",
  dicts: ['warehouse_type', 'sys_yes_no', 'shelf_type'],
  components: {
    ShelfFormDialog
  },
  props: {
    value: {
      type: Boolean,
      default: false
    },
    title: {
      type: String,
      default: ""
    },
    warehouseData: {
      type: Object,
      default: () => ({})
    }
  },
  data() {
    return {
      activeTab: 'basic',
      form: {},
      rules: {
        warehouseCode: [
          { required: true, message: "仓库编码不能为空", trigger: "blur" }
        ],
        warehouseName: [
          { required: true, message: "仓库名称不能为空", trigger: "blur" }
        ],
        warehouseType: [
          { required: true, message: "仓库类型不能为空", trigger: "change" }
        ]
      },
      // 货架相关 - 这些数据不会传递给仓库保存API
      shelfList: [],
      shelfLoading: false,
      shelfFormVisible: false,
      shelfFormTitle: "",
      currentShelf: {}
    }
  },
  computed: {
    visible: {
      get() {
        return this.value
      },
      set(val) {
        this.$emit('input', val)
      }
    }
  },
  watch: {
    value(val) {
      if (val) {
        this.initForm()
        if (this.form.id) {
          this.loadShelfList()
        }
      }
    }
  },
  methods: {
    initForm() {
      // 只复制仓库相关字段，避免传递货架等无关数据
      this.form = {
        id: this.warehouseData.id,
        warehouseCode: this.warehouseData.warehouseCode,
        warehouseName: this.warehouseData.warehouseName,
        warehouseType: this.warehouseData.warehouseType,
        manager: this.warehouseData.manager,
        phone: this.warehouseData.phone,
        address: this.warehouseData.address,
        remark: this.warehouseData.remark,
        isActive: this.warehouseData.isActive
      }
      
      if (!this.form.isActive) {
        this.form.isActive = '1'
      }
      this.activeTab = 'basic'
      
      console.log('Warehouse form initialized:', this.form)
    },
    
    // 加载货架列表
    loadShelfList() {
      console.log('loadShelfList called, form.id:', this.form.id)
      if (!this.form.id) {
        console.log('No warehouse ID, skipping shelf load')
        this.shelfList = [] // 新增仓库时，货架列表为空
        return
      }
      
      this.shelfLoading = true
      console.log('Calling getShelvesByWarehouse with ID:', this.form.id)
      
      getShelvesByWarehouse(this.form.id).then(response => {
        console.log('Shelf API response:', response)
        this.shelfList = response.data || []
        console.log('Shelf list updated:', this.shelfList)
        this.shelfLoading = false
      }).catch(error => {
        console.error('Error loading shelf list:', error)
        this.shelfList = [] // 出错时设置为空数组
        this.shelfLoading = false
      })
    },
    
    // 货架管理相关
    openShelfDialog() {
      this.currentShelf = {}
      this.shelfFormTitle = "添加货架"
      this.shelfFormVisible = true
    },
    
    handleEditShelf(shelf) {
      this.currentShelf = { ...shelf }
      this.shelfFormTitle = "修改货架"
      this.shelfFormVisible = true
    },
    
    handleDeleteShelf(shelf) {
      this.$modal.confirm('是否确认删除货架"' + shelf.shelfName + '"？').then(() => {
        return delShelf(shelf.id)
      }).then(() => {
        this.$modal.msgSuccess("删除成功")
        // 删除成功后重新加载货架列表
        if (this.form.id) {
          this.loadShelfList()
        }
      }).catch(error => {
        console.error('Shelf delete error:', error)
        this.$modal.msgError("删除失败")
      })
    },
    
    handleShelfSuccess() {
      this.shelfFormVisible = false
      // 货架操作成功后，重新加载货架列表
      if (this.form.id) {
        this.loadShelfList()
      }
    },
    
    submitForm() {
      this.$refs["warehouseRef"].validate(valid => {
        if (valid) {
          // 清理表单数据，只保留仓库相关字段，移除货架相关信息
          const warehouseData = {
            id: this.form.id,
            warehouseCode: this.form.warehouseCode,
            warehouseName: this.form.warehouseName,
            warehouseType: this.form.warehouseType,
            manager: this.form.manager,
            phone: this.form.phone,
            address: this.form.address,
            remark: this.form.remark,
            isActive: this.form.isActive
          }
          
          console.log('Submitting warehouse data:', warehouseData)
          
          if (this.form.id != null) {
            // 修改仓库 - 只保存仓库信息，不处理货架
            updateWarehouse(warehouseData).then(response => {
              this.$modal.msgSuccess("修改成功")
              this.$emit('success')
            }).catch(error => {
              console.error('Warehouse update error:', error)
              this.$modal.msgError("修改失败")
            })
          } else {
            // 新增仓库 - 只保存仓库信息
            addWarehouse(warehouseData).then(response => {
              this.$modal.msgSuccess("新增成功")
              this.$emit('success')
            }).catch(error => {
              console.error('Warehouse add error:', error)
              this.$modal.msgError("新增失败")
            })
          }
        }
      })
    },
    
    cancel() {
      this.visible = false
      this.reset()
    },
    
    reset() {
      // 重置仓库表单
      this.form = {}
      // 重置货架列表（但不影响数据库中的货架）
      this.shelfList = []
      this.activeTab = 'basic'
      this.resetForm("warehouseRef")
      
      console.log('Form reset completed')
    }
  }
}
</script>

<style scoped>
.warehouse-form-section {
  margin-bottom: 20px;
}

.add-shelf-section {
  margin-top: 15px;
  text-align: center;
  padding: 10px;
  background-color: #f5f7fa;
  border-radius: 4px;
}

.shelf-table-section {
  margin-top: 20px;
}

.shelf-table-section h3 {
  margin-bottom: 15px;
  color: #303133;
  font-size: 16px;
  font-weight: 600;
}

.shelf-table {
  margin-top: 10px;
}

.manager-input-group {
  display: flex;
  align-items: center;
}

.manager-input-group .el-input {
  margin-right: 10px;
}

.manager-input-group .el-button {
  flex-shrink: 0;
}

.dialog-footer {
  text-align: right;
}
</style>
