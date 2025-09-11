<template>
  <div class="app-container">
    <el-row :gutter="20">
      <!-- 统计卡片 -->
      <el-col :span="6">
        <el-card class="statistics-card" shadow="hover">
          <div class="stat-item">
            <div class="stat-icon total">
              <i class="el-icon-message"></i>
            </div>
            <div class="stat-content">
              <div class="stat-number">{{ statistics.totalMessages }}</div>
              <div class="stat-label">全部消息</div>
            </div>
          </div>
        </el-card>
      </el-col>
      <el-col :span="6">
        <el-card class="statistics-card" shadow="hover">
          <div class="stat-item">
            <div class="stat-icon unread">
              <i class="el-icon-bell"></i>
            </div>
            <div class="stat-content">
              <div class="stat-number">{{ statistics.unreadMessages }}</div>
              <div class="stat-label">未读消息</div>
            </div>
          </div>
        </el-card>
      </el-col>
      <el-col :span="6">
        <el-card class="statistics-card" shadow="hover">
          <div class="stat-item">
            <div class="stat-icon read">
              <i class="el-icon-check"></i>
            </div>
            <div class="stat-content">
              <div class="stat-number">{{ statistics.readMessages }}</div>
              <div class="stat-label">已读消息</div>
            </div>
          </div>
        </el-card>
      </el-col>
      <el-col :span="6">
        <el-card class="statistics-card" shadow="hover">
          <div class="stat-item">
            <div class="stat-icon sent">
              <i class="el-icon-s-promotion"></i>
            </div>
            <div class="stat-content">
              <div class="stat-number">{{ statistics.sentMessages }}</div>
              <div class="stat-label">已发送</div>
            </div>
          </div>
        </el-card>
      </el-col>
    </el-row>

    <el-row :gutter="20" style="margin-top: 20px;">
      <!-- 消息类型统计 -->
      <el-col :span="12">
        <el-card class="chart-card" shadow="hover">
          <div slot="header" class="card-header">
            <span class="header-title">
              <i class="el-icon-pie-chart"></i>
              消息类型统计
            </span>
          </div>
          <div id="messageTypeChart" style="height: 300px;"></div>
        </el-card>
      </el-col>
      
      <!-- 消息级别统计 -->
      <el-col :span="12">
        <el-card class="chart-card" shadow="hover">
          <div slot="header" class="card-header">
            <span class="header-title">
              <i class="el-icon-data-line"></i>
              消息级别统计
            </span>
          </div>
          <div id="messageLevelChart" style="height: 300px;"></div>
        </el-card>
      </el-col>
    </el-row>

    <el-row :gutter="20" style="margin-top: 20px;">
      <!-- 每日消息趋势 -->
      <el-col :span="24">
        <el-card class="chart-card" shadow="hover">
          <div slot="header" class="card-header">
            <span class="header-title">
              <i class="el-icon-data-analysis"></i>
              每日消息趋势
            </span>
            <el-date-picker
              v-model="dateRange"
              type="daterange"
              range-separator="至"
              start-placeholder="开始日期"
              end-placeholder="结束日期"
              value-format="yyyy-MM-dd"
              @change="getTrendData"
              style="width: 240px;"
            >
            </el-date-picker>
          </div>
          <div id="trendChart" style="height: 400px;"></div>
        </el-card>
      </el-col>
    </el-row>

    <!-- 详细统计表格 -->
    <el-card class="table-card" shadow="hover" style="margin-top: 20px;">
      <div slot="header" class="card-header">
        <span class="header-title">
          <i class="el-icon-s-grid"></i>
          详细统计
        </span>
        <el-button
          type="primary"
          icon="el-icon-download"
          @click="handleExport"
          v-hasPermi="['message:statistics:export']"
        >导出</el-button>
      </div>
      
      <el-table v-loading="loading" :data="statisticsList" stripe>
        <el-table-column label="日期" align="center" prop="date" width="120" />
        <el-table-column label="系统消息" align="center" prop="systemCount" />
        <el-table-column label="业务消息" align="center" prop="businessCount" />
        <el-table-column label="通知消息" align="center" prop="notificationCount" />
        <el-table-column label="普通级别" align="center" prop="normalCount" />
        <el-table-column label="重要级别" align="center" prop="importantCount" />
        <el-table-column label="紧急级别" align="center" prop="urgentCount" />
        <el-table-column label="已读消息" align="center" prop="readCount" />
        <el-table-column label="未读消息" align="center" prop="unreadCount" />
        <el-table-column label="总消息数" align="center" prop="totalCount" />
      </el-table>

      <pagination
        v-show="total > 0"
        :total="total"
        :page.sync="queryParams.pageNum"
        :limit.sync="queryParams.pageSize"
        @pagination="getStatisticsList"
      />
    </el-card>
  </div>
</template>

<script>
import { getMessageStatistics, getMessageStatisticsList } from "@/api/message/statistics";
import * as echarts from 'echarts';

export default {
  name: "MessageStatistics",
  data() {
    return {
      // 遮罩层
      loading: true,
      // 总条数
      total: 0,
      // 统计数据
      statistics: {
        totalMessages: 0,
        unreadMessages: 0,
        readMessages: 0,
        sentMessages: 0
      },
      // 统计列表
      statisticsList: [],
      // 日期范围
      dateRange: [],
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10
      },
      // 图表实例
      messageTypeChart: null,
      messageLevelChart: null,
      trendChart: null
    };
  },
  created() {
    this.getStatistics();
    this.getStatisticsList();
  },
  mounted() {
    this.$nextTick(() => {
      this.initCharts();
    });
  },
  beforeDestroy() {
    if (this.messageTypeChart) {
      this.messageTypeChart.dispose();
    }
    if (this.messageLevelChart) {
      this.messageLevelChart.dispose();
    }
    if (this.trendChart) {
      this.trendChart.dispose();
    }
  },
  methods: {
    /** 获取统计数据 */
    getStatistics() {
      getMessageStatistics().then(response => {
        this.statistics = response.data || this.statistics;
        this.updateCharts();
      });
    },
    /** 获取统计列表 */
    getStatisticsList() {
      this.loading = true;
      getMessageStatisticsList(this.addDateRange(this.queryParams, this.dateRange)).then(response => {
        this.statisticsList = response.rows || [];
        this.total = response.total || 0;
        this.loading = false;
      });
    },
    /** 获取趋势数据 */
    getTrendData() {
      this.getStatisticsList();
      this.updateTrendChart();
    },
    /** 初始化图表 */
    initCharts() {
      this.messageTypeChart = echarts.init(document.getElementById('messageTypeChart'));
      this.messageLevelChart = echarts.init(document.getElementById('messageLevelChart'));
      this.trendChart = echarts.init(document.getElementById('trendChart'));
      
      this.updateCharts();
    },
    /** 更新图表 */
    updateCharts() {
      this.updateMessageTypeChart();
      this.updateMessageLevelChart();
      this.updateTrendChart();
    },
    /** 更新消息类型图表 */
    updateMessageTypeChart() {
      if (!this.messageTypeChart) return;
      
      const option = {
        title: {
          text: '消息类型分布',
          left: 'center',
          textStyle: {
            fontSize: 16
          }
        },
        tooltip: {
          trigger: 'item',
          formatter: '{a} <br/>{b}: {c} ({d}%)'
        },
        legend: {
          orient: 'vertical',
          left: 'left'
        },
        series: [
          {
            name: '消息类型',
            type: 'pie',
            radius: '50%',
            data: [
              { value: this.statistics.systemMessages || 0, name: '系统消息' },
              { value: this.statistics.businessMessages || 0, name: '业务消息' },
              { value: this.statistics.notificationMessages || 0, name: '通知消息' }
            ],
            emphasis: {
              itemStyle: {
                shadowBlur: 10,
                shadowOffsetX: 0,
                shadowColor: 'rgba(0, 0, 0, 0.5)'
              }
            }
          }
        ]
      };
      
      this.messageTypeChart.setOption(option);
    },
    /** 更新消息级别图表 */
    updateMessageLevelChart() {
      if (!this.messageLevelChart) return;
      
      const option = {
        title: {
          text: '消息级别分布',
          left: 'center',
          textStyle: {
            fontSize: 16
          }
        },
        tooltip: {
          trigger: 'item',
          formatter: '{a} <br/>{b}: {c} ({d}%)'
        },
        legend: {
          orient: 'vertical',
          left: 'left'
        },
        series: [
          {
            name: '消息级别',
            type: 'pie',
            radius: '50%',
            data: [
              { value: this.statistics.normalMessages || 0, name: '普通' },
              { value: this.statistics.importantMessages || 0, name: '重要' },
              { value: this.statistics.urgentMessages || 0, name: '紧急' }
            ],
            emphasis: {
              itemStyle: {
                shadowBlur: 10,
                shadowOffsetX: 0,
                shadowColor: 'rgba(0, 0, 0, 0.5)'
              }
            }
          }
        ]
      };
      
      this.messageLevelChart.setOption(option);
    },
    /** 更新趋势图表 */
    updateTrendChart() {
      if (!this.trendChart) return;
      
      // 模拟趋势数据
      const dates = [];
      const readData = [];
      const unreadData = [];
      
      for (let i = 6; i >= 0; i--) {
        const date = new Date();
        date.setDate(date.getDate() - i);
        dates.push(date.toISOString().split('T')[0]);
        readData.push(Math.floor(Math.random() * 50) + 20);
        unreadData.push(Math.floor(Math.random() * 20) + 5);
      }
      
      const option = {
        title: {
          text: '每日消息趋势',
          left: 'center',
          textStyle: {
            fontSize: 16
          }
        },
        tooltip: {
          trigger: 'axis',
          axisPointer: {
            type: 'cross'
          }
        },
        legend: {
          data: ['已读消息', '未读消息'],
          top: 30
        },
        grid: {
          left: '3%',
          right: '4%',
          bottom: '3%',
          containLabel: true
        },
        xAxis: {
          type: 'category',
          boundaryGap: false,
          data: dates
        },
        yAxis: {
          type: 'value'
        },
        series: [
          {
            name: '已读消息',
            type: 'line',
            stack: 'Total',
            data: readData,
            smooth: true,
            itemStyle: {
              color: '#67C23A'
            }
          },
          {
            name: '未读消息',
            type: 'line',
            stack: 'Total',
            data: unreadData,
            smooth: true,
            itemStyle: {
              color: '#F56C6C'
            }
          }
        ]
      };
      
      this.trendChart.setOption(option);
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('message/statistics/export', {
        ...this.queryParams
      }, `statistics_${new Date().getTime()}.xlsx`)
    }
  }
};
</script>

<style scoped>
.app-container {
  padding: 20px;
  background: #f5f7fa;
  min-height: calc(100vh - 84px);
}

.statistics-card {
  border-radius: 12px;
  border: none;
  margin-bottom: 20px;
}

.chart-card {
  border-radius: 12px;
  border: none;
  margin-bottom: 20px;
}

.table-card {
  border-radius: 12px;
  border: none;
}

.card-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 0;
}

.header-title {
  display: flex;
  align-items: center;
  font-size: 16px;
  font-weight: 600;
  color: #303133;
}

.header-title i {
  margin-right: 8px;
  font-size: 18px;
  color: #409eff;
}

.stat-item {
  display: flex;
  align-items: center;
  padding: 20px;
}

.stat-icon {
  width: 60px;
  height: 60px;
  border-radius: 12px;
  display: flex;
  align-items: center;
  justify-content: center;
  margin-right: 20px;
  font-size: 28px;
  color: white;
}

.stat-icon.total {
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
}

.stat-icon.unread {
  background: linear-gradient(135deg, #f093fb 0%, #f5576c 100%);
}

.stat-icon.read {
  background: linear-gradient(135deg, #4facfe 0%, #00f2fe 100%);
}

.stat-icon.sent {
  background: linear-gradient(135deg, #43e97b 0%, #38f9d7 100%);
}

.stat-content {
  flex: 1;
}

.stat-number {
  font-size: 32px;
  font-weight: 700;
  color: #303133;
  line-height: 1;
  margin-bottom: 8px;
}

.stat-label {
  font-size: 14px;
  color: #909399;
  font-weight: 500;
}

::v-deep .el-card__header {
  border-bottom: 1px solid #ebeef5;
  padding: 20px 24px 16px;
}

::v-deep .el-card__body {
  padding: 24px;
}

::v-deep .el-table {
  border-radius: 8px;
  overflow: hidden;
}

::v-deep .el-table th {
  background-color: #fafafa;
  color: #606266;
  font-weight: 600;
}

::v-deep .el-button {
  border-radius: 6px;
  font-weight: 500;
}
</style>
