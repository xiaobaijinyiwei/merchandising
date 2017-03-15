package main.java.servlet;

import java.awt.Color;
import java.awt.Font;
import java.io.IOException;

import javax.servlet.http.HttpSession;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartRenderingInfo;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.entity.StandardEntityCollection;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.PolarAxisLocation;
import org.jfree.chart.renderer.category.LineAndShapeRenderer;
import org.jfree.chart.servlet.ServletUtilities;
import org.jfree.chart.title.TextTitle;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.ui.RectangleEdge;

public class DrawHistogram {

	private int width;// 图象宽度

	private int height; // 图象高度

	private String chartTitle;// 图表标题

	private String subtitle;// 副标题

	private String xTitle;// X轴标题

	private String yTitle;// Y轴标题

	private String[] cutline;// 图例名称

	private String category[]; // 统计种类

	private double[][] data;// 绘图数据

	private String servletURI = "/DisplayChart";// 映射路径

	public DrawHistogram() {
		this.width = 1260;
		this.height = 600;
	}
	/*
	public DrawHistogram(List<Commodity> list) {
		this();
		if (list.size()>0) {
			this.chartTitle = "三月份销售详情";
			this.subtitle = "------时间：2017年";
			this.xTitle = "日期";
			this.yTitle = "销售量 ";
			this.cutline = new String[] { "销售数量", "进价", "盈利" };
			String[] str1 = new String[list.size()];
			double[][] d1 = new double[3][list.size()];
			for (int i = 0; i <list.size(); i++) {
				Commodity commodity = list.get(i);
				str1[i]=commodity.getDate();
				d1[0][i]=commodity.getsNumber();
				d1[1][i]=commodity.getsPrice();
				d1[2][i]=commodity.getpPrice();
			}
			this.category=str1;
			this.data=d1;
					
		}
	}
	*/
	public DrawHistogram(String chartTitle,String subtitle, String xTitle,
			String yTitle, String[] cutline,String[] category, double[][] data) {
		this();
		this.chartTitle = chartTitle;
		this.subtitle = subtitle;
		this.xTitle = xTitle;
		this.yTitle = yTitle;
		this.cutline = cutline;
		this.category = category;
		this.data = data;
	}

	public DrawHistogram(String chartTitle,
			String subtitle, String xTitle, String yTitle, String[] cutline,
			String[] category, double[][] data, String servletURI) {
		this();
		this.chartTitle = chartTitle;
		this.subtitle = subtitle;
		this.xTitle = xTitle;
		this.yTitle = yTitle;
		this.cutline = cutline;
		this.category = category;
		this.data = data;
		this.servletURI = servletURI;
	}

	public String draw(HttpSession session, String contextPath) {

		// 创建绘图数据集
		DefaultCategoryDataset dataset = new DefaultCategoryDataset();
		for (int m = 0; m < cutline.length; m++) {
			for (int n = 0; n < category.length; n++) {
				dataset.addValue(data[m][n], cutline[m], category[n]);
			}
		}

		// 创建图表对象

		JFreeChart chart = ChartFactory.createLineChart(
				chartTitle, // 图表标题
				xTitle, // X轴标题
				yTitle, // Y轴标题
				dataset, // 绘图数据集
				PlotOrientation.VERTICAL, // 柱形图绘制方向
				true, // 显示图例
				true, // 采用标准生成器
				false // 生成链接
				);
		//解决中文乱码
	 	CategoryPlot categoryplot = (CategoryPlot) chart.getPlot();
        NumberAxis numberaxis = (NumberAxis) categoryplot.getRangeAxis();  
        CategoryAxis domainAxis = categoryplot.getDomainAxis();  
        TextTitle textTitle = chart.getTitle();
        textTitle.setFont(new Font("黑体", Font.PLAIN, 25));  
        domainAxis.setTickLabelFont(new Font("sans-serif", Font.PLAIN, 12));  
        domainAxis.setLabelFont(new Font("宋体", Font.PLAIN, 12));  
        numberaxis.setTickLabelFont(new Font("sans-serif", Font.PLAIN, 12));  
        numberaxis.setLabelFont(new Font("黑体", Font.PLAIN, 12));  
        chart.getLegend().setItemFont(new Font("宋体", Font.PLAIN, 14));
        
        //设置图片背景色
        //chart.setBackgroundPaint(new java.awt.Color(189,235,255));
        
        CategoryPlot plot = (CategoryPlot) chart.getPlot();      
        plot.setBackgroundPaint(new Color(239,251,255));//生成图片中墙体的背景色
        plot.setRangeGridlinePaint(Color.black);//生成图片中格子线的颜色        
        plot.setNoDataMessage("没有相关统计数据"); // 没有数据时显示的消息  
        
       //设置折线颜色
        LineAndShapeRenderer renderer = (LineAndShapeRenderer) plot.getRenderer();
        renderer.setSeriesPaint(0, Color.RED);
        renderer.setSeriesPaint(1, Color.GREEN);
        renderer.setSeriesPaint(2, Color.BLUE);
        renderer.setSeriesPaint(3, Color.CYAN);
        
        //设置图例在右方显示
        chart.getLegend().setVisible(true);
        chart.getLegend().setPosition(RectangleEdge.RIGHT);
        
        
		// 添加副标题
		chart.addSubtitle(new TextTitle(subtitle));     	
		ChartRenderingInfo info = new ChartRenderingInfo(
				new StandardEntityCollection());
		// 生成指定格式的图片，并返回图片名称
		String fileName = "";
		try {
			fileName = ServletUtilities.saveChartAsPNG(chart, width, height,
					info, session);
		} catch (IOException e) {
			System.out.println("------ 在绘制图片时抛出异常，内容如下：");
			e.printStackTrace();
		}
		// 组织图片浏览路径
		String graphURL = contextPath + servletURI + "?filename=" + fileName;
		// 返回图片浏览路径
		return graphURL;

	}
}