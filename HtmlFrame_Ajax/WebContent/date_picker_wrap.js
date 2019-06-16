			Calendar=function(){
			};
			// execute function for Singleton
			Calendar.setup=function(inputField,
						ifFormat,
						button,
						imagePath,
						regionLetter){
				new CalendarExecutor(inputField,ifFormat,button,imagePath,regionLetter);
			} 
			// Object
			function CalendarExecutor(inputField, 
						ifFormat, 
						button, 
						imagePath, 
						regionLetter){
				this.regionLetter=regionLetter||'ru';
				this.imagePath=imagePath||'images/calendar.gif';			
				this.dateFormat='dd.mm.yy';	

		  		$.datepicker.setDefaults($.extend($.datepicker.regional[this.regionLetter]));
				var element_name='#'+inputField.inputField;
				$(element_name).datepicker({dateFormat:this.dateFormat,
								     buttonImageOnly: true, showOn: 'button', // both 
								     buttonImage: this.imagePath});
		  			//$("#test_field").datepicker();
				var image_element='#'+inputField.button;
				$(image_element).hide();
			}
