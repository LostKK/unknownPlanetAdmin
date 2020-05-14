<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

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
	            <span>文章信息</span>
	        </li>
	    </ul>
	</div>
	<!-- END PAGE BAR -->
	<!-- END PAGE HEADER-->
                        
	<div class="row">
    	<div class="col-md-12">
			<br/>
			<!-- 意见反馈 start -->
			<div class="tabbable-line boxless tabbable-reversed">
            	<div class="portlet box green-jungle">
                	<div class="portlet-title">
                    	<div class="caption">
                    		<i class="icon-plus"></i>添加文章
                    	</div>
					</div>
					
					<div class="portlet-body form">
					
	                    <!-- BEGIN FORM-->
	                    <form id="addWordForm" class="form-horizontal">
		                    <div class="form-body">
		                    
		                    	<div class="form-group">
		                        	<label class="col-md-3 control-label"><span class="field-required"> * </span>作者：</label>
		                            <div class="col-md-4">
		                            	<div id="input-error">
		                            		<input id="author" name="author" type="text" class="form-control" placeholder="1-4字">
		                            	</div>
									</div>
								</div>
								
								<div class="form-group">
		                        	<label class="col-md-3 control-label"><span class="field-required"> * </span>文章：</label>
		                            <div class="col-md-4">
		                            	<div id="input-error">
		                            		<input id="words" name="words" type="text" class="form-control" placeholder="1-50字">
		                            	</div>
									</div>
								</div>
								
								<div class="form-group">
		                        	<label class="col-md-3 control-label"><span class="field-required"> * </span>配图</label>
		                            <div class="col-md-4">
		                            	<div id="input-error">
	                            			<input type="hidden" id="path" name="path" class="form-control"/>

											<input id="file" type="file" name="file" data-url="<%=request.getContextPath() %>/word/wordUpload.action" accept=".jpg,.png"/>
											
											<div id="wordContent"></div>
	                            		</div>
	                            		
									</div>
								</div>
		                                                  
							</div>
	                                                        
							<div class="form-actions">
			                    <div class="row">
			                        <div class="col-md-offset-3 col-md-9">
			                            <button type="submit" class="btn green-jungle">提 交</button>
			                            <button type="reset" class="btn grey-salsa btn-outline">取  消</button>
			                        </div>
			                    </div>
							</div>
						</form>
						<!-- END FORM-->
						
					</div>
				</div>
			</div>
                            
		</div>
	</div>
	
<script type="text/javascript">
    
    $("#file").fileupload({
    	pasteZone: "#wordContent",
    	dataType: "json",
    	done: function(e, data) {
    		console.log(data);
    		
    		if (data.result.status != '200') {
    			alert("长传失败...");
    		} else {
    			/* var bgmServer = $("#bgmServer").val();
    			var url = bgmServer + data.result.data; 
    			$("#bgmContent").html("<a href='" + url + "' target='_blank'>点我播放</a>");
    			$("#path").attr("value", data.result.data);*/
    			var apiServer = $("#apiServer").val();
    			var url = apiServer + data.result.data;
    			$("#wordContent").html("<arc href='" + url + "' target='_blank'>点我查看</a>");
    			$("#path").attr("value", data.result.data);
    			
    		}
    		
    	}
    });
    
    var submitWord = function() {
    	$('#addWordForm').ajaxSubmit({
    		url: $('#hdnContextPath').val() + '/word/addWord.action',
    		type: 'POST',
    		success: function(data) {
    			
    			if (data.status == 200 && data.msg == 'OK') {
    				alert('Word添加成功...')
    			} else {
    				alert('Word添加失败...')
    			}
    			
    			$("#wordListMenu").click();
    		}
    	});
    }
    
    $('#addWordForm').validate({
    	errorElement: 'span', //default input error message container
        errorClass: 'help-block', // default input error message class
        focusInvalid: false, // do not focus the last invalid input
        ignore: "", // validate all fields including form hidden input
        rules: {
        	author: {
                required: true,
                rangelength: [1,8]
            },
            words: {
                required: true,
                rangelength: [1,50]
            },
            path: {
                required: true
            }
        },
        messages: {
        	author: {
                required: "作者不能为空.",
                rangelength: "作者长度请控制在1-8位."
            },
            name: {
                required: "内容不能为空.",
                rangelength: "内容长度请控制在1-50位."
            },
            path: {
                required: "路径不能为空."
            }
        },
        invalidHandler: function(event, validator) { //display error alert on form submit   
            $('.alert-danger', $('#addWordForm')).show();
        },

        highlight: function(element) { // hightlight error inputs
            $(element).closest('.form-group').addClass('has-error'); // set error class to the control group
        },
        success: function(label) {
            label.closest('.form-group').removeClass('has-error');
            label.remove();
        },
        errorPlacement: function(error, element) {
            error.insertAfter(element.closest('#input-error'));
        },
        submitHandler: function(form) {
        	// FIXME
        	submitWord();
        }
    });
    
    
</script>
