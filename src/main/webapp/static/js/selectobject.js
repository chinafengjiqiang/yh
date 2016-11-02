
//function del(){
//	var destList = parent.dest.document.getElementById("destList");
//	if(destList.selectedIndex>=0){
//	destList.options.remove(destList.selectedIndex);
//	}
//}


function addElement(destList,id,name)
{
	var exist=false;
	for(var i=0;i<destList.length;i++)
	{
		var op=destList.options[i];
		if(op.value==id)
		{
		exist=true;
		break;
		}
	}
	if(!exist)
	{
	var op1=new Option(name,id);
	op1.title = name;
	destList.options.add(op1);
	}
/*	else
	{
	alert("ID="+id+"的对象已存在");
	}
*/
}

function save(receiverName,elementType)
{
	var destList=dest.document.getElementById("destList");
	if("INPUT" == elementType){
	   var op=destList.options[0];
       var objId = Parent.document.getElementById(receiverName);
       var objName = Parent.document.getElementById(receiverName+"_NAME");
       objId.value = op.value;
       objName.value = op.text;
	}else{
	       for(var i=0;i<destList.length;i++)
            {
                if(Parent!=null){
                    var deptList =  Parent.document.getElementById(receiverName);
                    var exist=false;
                    for(var j=0;j<deptList.length;j++){
                        var op=deptList.options[j];
                        if(op.value==destList.options[i].value)
                        {
                            exist=true;
                            break;
                        }
                    }
                    if(!exist)
                    {
                        var op1=new Option(destList.options[i].text,destList.options[i].value,true,true);
                        Parent.document.getElementById(receiverName).options.add(op1);
                    }       
        
                }
            }
	}
	Dialog.close();
}


function removeObj(objName){
	var deptList = document.getElementById(objName);
	if(deptList.selectedIndex>=0){
		deptList.remove(deptList.selectedIndex);
	}
}



function add(){
	var sourceList=document.getElementById("sourceList");
	if(sourceList!=null&&sourceList.selectedIndex>=0)
	{
	var op=sourceList.options[sourceList.selectedIndex];	
	var destList = document.getElementById("destList");
	addElement(destList,op.value,op.text);
	}
	else
	{
		alert("没有选中的源对象！");
	}

}


function addAll(){
	var sourceList=document.getElementById("sourceList");
	if(sourceList!=null){
		for(var i=0;i<sourceList.length;i++){
			var op=sourceList.options[i];	
			var destList = document.getElementById("destList");
			addElement(destList,op.value,op.text);
		}
	}

}


function delAll(){
	var destList = dest.document.getElementById("destList");
	while(destList.length>0){
		destList.remove(0);
	}

}


