angular.module('app').controller('fileController', function($localStorage, $scope, $http, $rootScope, $state) {
	$scope.path = $localStorage.session.currentPath;
	$scope.user = $localStorage.session.user;
	$scope.content = "";
	$scope.extension = "txt";
	
	$scope.newFileName = "";
	
	if ($localStorage.clickedFile != undefined) {
		$scope.newFileName = $localStorage.clickedFile.name;
		$scope.content = $localStorage.clickedFile.content;
	}
	
	console.log($localStorage.clickedFile + " CLICKED FILE");
	
	$scope.saveFile = function() {
		path = formatPathToApiPattern($scope.path);
		
		var div = document.createElement("div");
		div.innerHTML = $scope.content;
		var parsedContent = div.textContent || div.innerText || "";
		
		requestData = {};
		requestData.user = $scope.user;
		requestData.fileName = $scope.newFileName;
		requestData.fileExtension = $scope.extension;
		requestData.fileContent = parsedContent;
		
		console.log(requestData);
		
		
		
		$http.post('/server/userdirectory/newfile/' + path, requestData)
		.then(function(response) {
			
			$localStorage.session.user = response.data;
			window.alert("File successfully created.");
			$state.go('dashboard.directories', {'folderPath': $localStorage.session.currentPath});
			$scope.path = $localStorage.session.currentPath;
			
		}, function(response) {
			
			window.alert(response.data.message);
			
	});
	}
	
	function formatPathToApiPattern(path) {
		tempPath = path.replace(new RegExp('/', 'g'), '-').replace("root/", "").replace("root", "")
		return tempPath.substring(1, tempPath.length) + "/" + $scope.newFileName;
	}
	
	$scope.directoriesView = function() {
		$state.go('dashboard.directories', {folderPath: $localStorage.session.currentPath});
	}
});