function videoPreview(){
    var videoname = document.getElementById("chooseVideo");
    videoname = videoname.options[videoname.selectedIndex].text;
    if (videoname === undefined || videoname === null) {
        alert("Please choose video.");
    }
    window.open("http://202.45.128.135:28013/ct/data/videos/" + videoname + ".mp4");
}

function doUploadFile() {
	//confirm user selecting a file
	var files = document.getElementById("fileToUpload").files;
	if (files === undefined || files.length <= 0) {
		alert("No file selected");
		return;
	}

	var videoname = document.getElementById("chooseVideo");
	videoname = videoname.options[videoname.selectedIndex].text;

	//init for ajax file upload
	var file = files[0];
	// var url = "simple_upload" + document.location.search;
    var url = "find_picture_spark?videoname=" + videoname;
    var formData = new FormData();
	formData.append('file',file);

	$.ajax({
		url: url,  //Server script to process data
		type: "POST",
		data: formData,
		cache: false,
		contentType: false,
		processData: false,
		success: function(data) {
			var ret = data;
			if (ret.errcode === 0) {
				alert("upload successful");

				var imageidtemplate = "return_image";
				//	var imageid = "#" + imageidtemplate + 1;
				// $(imageid).attr("src", "http://localhost:8080/CCCBackend_war_exploded/data/search_image_by_video/1001000.png");

				var timestamp = ret.timestamp;
				for (var i = 0; i < 5; i++) {
				    if (ret.timeStamps[i] != 0) {
                        var imageid = "#" + imageidtemplate + (i + 1);
                        var src = "http://202.45.128.135:28013/ct/data/search_image_by_video/" + timestamp + "_" + ret.timeStamps[i] + ".png";
                        $(imageid).attr("src", src);
                    }
				}
			} else {
				alert("upload failed: "+ ret.errmsg);
			}
		},
		error:function(e) {
			alert("upload failed");
		}
	});
}

function bs_input_file() {
	$(".input-file").before(
		function() {
			if ( ! $(this).prev().hasClass('input-ghost') ) {
				var element = $("<input id='fileToUpload' type='file' class='input-ghost' onchange='readURL(this);' style='visibility:hidden; height:0'>");
				element.attr("name",$(this).attr("name"));
				element.change(function() {
					var input = element.next(element).find('input');
					if (input.files && input.files[0]) {
						var reader = new FileReader();
						reader.onload = function (e) {
							$('#upload_image')
								.attr('src', e.target.result)
						};
						reader.readAsDataURL(input.files[0]);
					}
					element.next(element).find('input').val((element.val()).split('\\').pop());
				});
				$(this).find("button.btn-choose").click(function(){
					element.click();
				});
				$(this).find("button.btn-reset").click(function(){
					element.val(null);
					$(this).parents(".input-file").find('input').val('');
				});
				$(this).find('input').css("cursor","pointer");
				$(this).find('input').mousedown(function() {
					$(this).parents('.input-file').prev().click();
					return false;
				});
				return element;
			}
		}
	);
}

function readURL(input) {
	if (input.files && input.files[0]) {
		var reader = new FileReader();
		reader.onload = function (e) {
			$('#upload_image')
				.attr('src', e.target.result)
		};
		reader.readAsDataURL(input.files[0]);
	}
}

$(function() {
    bs_input_file();
    $("#submitImage").click(doUploadFile);
});