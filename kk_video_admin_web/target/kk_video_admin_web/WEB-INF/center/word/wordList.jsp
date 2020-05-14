<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<script src="<%=request.getContextPath() %>/static/pages/js/wordList.js?v=1.1" type="text/javascript"></script>

	<!-- BEGIN PAGE HEADER-->
	<!-- BEGIN PAGE BAR -->
	<div class="page-bar">
	    <ul class="page-breadcrumb">
	        <li>
	            <span>首页</span>
	            <i class="fa fa-circle"></i>
	        </li>
	        <li>
	            <span>文章信息</span>
	            <i class="fa fa-circle"></i>
	        </li>
	        <li>
	            <span>文章列表</span>
	        </li>
	    </ul>
	</div>
	<!-- END PAGE BAR -->
	<!-- END PAGE HEADER-->
        
    <!-- 文章信息列表 jqgrid start -->                
	<div class="row">
	
		<!-- 搜索内容 -->
		<div class="col-md-12">
			<br/>
				<form id="searchWordListForm" class="form-inline" method="post" role="form">
					<div class="form-group">
						<label class="sr-only" for="writter">作者名:</label>
						<input id="writter" name="writter" type="text" class="form-control" placeholder="作者名" />
					</div>
					<!-- <div class="form-group">
						<label class="sr-only" for="nickname">昵称:</label>
						<input id="nickname" name="nickname" type="text" class="form-control" placeholder="昵称" />
					</div> -->
					<button id="searchWordListButton" class="btn yellow-casablanca" type="button">搜    索</button>
				</form>
			</div>
	
	
    	<div class="col-md-12">
			<br/>
			
			<div class="usersList_wrapper">  
			    <table id="wordList"></table>  
			    <div id="wordListPager"></div>  
			</div>  
			
		</div>
	</div>
	<!-- 用户信息列表 jqgrid end -->
	
