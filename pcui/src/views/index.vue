<template>
  <div class="app-container home">
    <el-row :gutter="20">
      <el-col :sm="24" :lg="12" style="padding-left: 20px">
        <h2>金鑫泉后台管理框架</h2>
        <p>
          做了十几年的ERP，一直都想要有一套适合国内企业的系统。与客户沟通了需求之后，就有了金鑫泉智造系统，一期主要是包含制造企业的基本业务。后续还会继续完善，希望逐渐完善。
        </p>

      </el-col>

      <el-col :sm="24" :lg="12" style="padding-left: 50px">
        <el-row>
          <el-col :span="12">
            <h2>技术选型</h2>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="6">
            <h4>后端技术</h4>
            <ul>
              <li>SpringBoot</li>
              <li>Spring Security</li>
              <li>JWT</li>
              <li>MyBatis</li>
              <li>Druid</li>
              <li>Fastjson</li>
              <li>...</li>
            </ul>
          </el-col>
          <el-col :span="6">
            <h4>前端技术</h4>
            <ul>
              <li>Vue</li>
              <li>Vuex</li>
              <li>Element-ui</li>
              <li>Axios</li>
              <li>Sass</li>
              <li>Quill</li>
              <li>...</li>
            </ul>
          </el-col>
        </el-row>
      </el-col>
    </el-row>
    <el-divider />
    <el-row :gutter="20">
      <el-col :xs="24" :sm="24" :md="8" :lg="8">
        <el-card class="update-log">
          <div slot="header" class="clearfix">
            <span>已完成的模块</span>
          </div>
          <div class="body">
            <h4>ERP模块 (15个)</h4>
            <ul>
              <li>✅ 物料分类管理</li>
              <li>✅ 物料主数据管理</li>
              <li>✅ <strong>BOM管理</strong> (新增)</li>
              <li>✅ BOM明细管理</li>
              <li>✅ <strong>生产订单管理</strong> (新增)</li>
              <li>✅ <strong>生产报工管理</strong> (新增)</li>
              <li>✅ <strong>工艺参数监控</strong> (新增)</li>
              <li>✅ 供应商管理</li>
              <li>✅ 客户管理</li>
              <li>✅ 工作中心管理</li>
              <li>✅ 设备管理</li>
              <li>✅ 模具管理</li>
              <li>✅ 工艺路线管理</li>
              <li>✅ 生产线管理</li>
              <li>✅ 工序管理</li>
            </ul>
          </div>
        </el-card>
      </el-col>

      <el-col :xs="24" :sm="24" :md="8" :lg="8">
        <el-card class="update-log">
          <div slot="header" class="clearfix">
            <span>已创建的数据库表结构</span>
          </div>
          <div class="body">
            <h4>ERP数据库表</h4>
            <p>已创建完整的ERP数据库表结构，包含：</p>
            <ul>
              <li>物料管理相关表 (erp_material_*)</li>
              <li><strong>BOM管理相关表 (erp_bom, erp_bom_detail, erp_bom_route_detail)</strong></li>
              <li><strong>生产订单相关表 (erp_production_order, erp_production_order_detail, erp_production_order_materials)</strong></li>
              <li><strong>生产报工相关表 (erp_production_report)</strong></li>
              <li><strong>工艺参数监控相关表 (erp_process_monitoring, erp_process_para)</strong></li>
              <li>供应商客户表 (erp_supplier, erp_customer)</li>
              <li>工作中心设备表 (erp_work_center, erp_equipment)</li>
              <li>模具管理表 (erp_mold*, erp_mold_detail)</li>
              <li>工序管理表 (erp_process)</li>
              <li>工艺路线表 (erp_process_route*, erp_process_route_detail)</li>
              <li>生产线管理表 (erp_production_line*)</li>
            </ul>
          </div>
        </el-card>
      </el-col>

      <el-col :xs="24" :sm="24" :md="8" :lg="8">
        <el-card class="update-log">
          <div slot="header" class="clearfix">
            <span>技术架构</span>
          </div>
          <div class="body">
            <h4>系统架构</h4>
            <ul>
              <li>后端: Spring Boot + MyBatis + Spring Security</li>
              <li>前端: Vue.js + Element UI</li>
              <li>数据库: MySQL 8.0+ (utf8mb4编码)</li>
              <li>权限: 完整的RBAC权限控制</li>
            </ul>
          </div>
        </el-card>
      </el-col>
    </el-row>
  </div>
</template>

<script>
export default {
  name: "Index",
  data() {
    return {
      // 版本号
      version: "3.9.0"
    }
  },
  methods: {
    goTarget(href) {
      window.open(href, "_blank")
    }
  }
}
</script>

<style scoped lang="scss">
.home {
  blockquote {
    padding: 10px 20px;
    margin: 0 0 20px;
    font-size: 17.5px;
    border-left: 5px solid #eee;
  }
  hr {
    margin-top: 20px;
    margin-bottom: 20px;
    border: 0;
    border-top: 1px solid #eee;
  }
  .col-item {
    margin-bottom: 20px;
  }

  ul {
    padding: 0;
    margin: 0;
  }

  font-family: "open sans", "Helvetica Neue", Helvetica, Arial, sans-serif;
  font-size: 13px;
  color: #676a6c;
  overflow-x: hidden;

  ul {
    list-style-type: none;
  }

  h4 {
    margin-top: 0px;
  }

  h2 {
    margin-top: 10px;
    font-size: 26px;
    font-weight: 100;
  }

  p {
    margin-top: 10px;

    b {
      font-weight: 700;
    }
  }

  .update-log {
    ol {
      display: block;
      list-style-type: decimal;
      margin-block-start: 1em;
      margin-block-end: 1em;
      margin-inline-start: 0;
      margin-inline-end: 0;
      padding-inline-start: 40px;
    }
  }
}
</style>

