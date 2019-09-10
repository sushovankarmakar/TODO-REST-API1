var toDoListArray = [];

window.onload = function (){
	getTodos();
}


function getTodos() {
	fetch("http://localhost:8090/todos", {
			method : "get"
	}).then(response => {
		return response.json()
	}).then(data => {
		toDoListArray = data.todo;
		console.log(toDoListArray.length);
		console.log(data.todo);
		 for(let i = 0; i < toDoListArray.length; i++ ){
			console.log(i+" "+toDoListArray[i]);
			 
			createTodoRow(toDoListArray[i]);
		 }
	});
}

function createTodoRow(inputValue){
	var text = document.createTextNode(inputValue);

	var tableRow = document.createElement("tr");
	
	var editButton = document.createElement("button");
	editButton.type = "button";
	editButton.innerHTML = "Edit";
	editButton.onclick = function(){
		editElement(this);
	} 

	var deleteButton = document.createElement("button");
	deleteButton.type = "button";
	deleteButton.innerHTML = "Delete";
	deleteButton.onclick = function(){
		deleteElement(this);	
	}

	tableRow.insertCell(0).appendChild(text);
	tableRow.insertCell(1).appendChild(editButton);
	tableRow.insertCell(2).appendChild(deleteButton);

	addToDataList(inputValue);
	
	document.getElementById("toDoTable").appendChild(tableRow);
}


function addNewElement(){

	var inputValue = document.getElementById("textInput").value;
	if(inputValue == ''){
		alert("You should write something");
	}
	else{
		//toDoListArray.push(inputValue);
		
		let requestBody = {todoname : inputValue};
		fetch("http://localhost:8090/todos",{
				method : "post",
				body : JSON.stringify(requestBody)
		}).then(response => {
			return response.json()
		}).then(data => {
			createTodoRow(inputValue);
		})
	}
	document.getElementById("textInput").value = "";	
}


function searchElement(){
	var inputValue = document.getElementById("textInput").value;
	if(toDoListArray.includes(inputValue)) {
		alert("Found");
	}
	else {
		alert("Not Found");
	}
}


function editElement(editButton){
	editButton.innerHTML = "Save";
	
	var taskName = editButton.parentNode.parentNode.getElementsByTagName("td")[0];
	taskName.innerHTML = "";

	var textBox = document.createElement("input");
	textBox.type = "text";
	textBox.placeholder = "Edit your task";

	taskName.appendChild(textBox);
	//editButton.parentNode.parentNode.insertCell(0);

	editButton.onclick = function(){

		let requestBody = {todoname : textBox.value};
		let url = "http://localhost:8090/todos/" + (editButton.parentNode.parentNode.rowIndex - 1);
		fetch(url, {
			method: "put",
			body: JSON.stringify(requestBody)
		}).then(response =>{
			return response.json()
		}).then( data => {

			var val = document.createTextNode(textBox.value);
			taskName.appendChild(val); 
        	taskName.removeChild(textBox);
			editButton.innerHTML = "Edit";
			editButton.onclick = function() {
				editElement(this);
			}
		})

		//toDoListArray.push(val);
	}
}

function deleteElement(deleteButton){
	var toDoTable = document.getElementById("toDoTable");
	var rowToDelete = deleteButton.parentNode.parentNode;
	
	let url =  "http://localhost:8090/todos/" + (rowToDelete.rowIndex - 1);
	fetch(url, {
		method: "delete"
	}).then(response => {
		return response.json()
	}).then(data => {
		toDoTable.deleteRow(rowToDelete.rowIndex);
		toDoListArray.splice(rowToDelete.rowIndex[0]);
	})
}

function addToDataList(item){
	var listItem = document.getElementById("listItems");
	var optionTag = document.createElement("option");
	optionTag.setAttribute("value", item);
	listItem.appendChild(optionTag);
}

