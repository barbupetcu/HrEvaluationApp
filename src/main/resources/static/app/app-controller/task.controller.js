(function () {
    'use strict';

    angular
        .module('app')
        .controller('TaskController', TaskController);

    TaskController.$inject = ['UserService', '$rootScope', 'FlashService','$uibModal', 'DataService', '$interval', '$q', '$http'];
    function TaskController(UserService, $rootScope, FlashService, $uibModal, DataService, $interval, $q, $http) {
        var vm = this;
        vm.activeTask = '';
        vm.tasksData=[];
        vm.filterColumn = [{id: 1, column: 'Data Creari'},
            {id: 2, column: 'Deadline'}];

        vm.filterType = [{id: 1, type: 'between'},
            {id: 2, type: 'after'},
            {id: 3, type: 'before'},
            {id: 4, type: 'equal'},
        ];
        vm.creazaRap = false;



        (function initController() {
            loadTasks();
        })();

        function loadTasks(){
            UserService.getTasksByDept().then(function(response){
                vm.tasksData = response.data;
                vm.activeTask=vm.tasksData[0].id
            });
        }

        vm.creazaRaport = function creazaRaport(flag){
            vm.creazaRap = flag;
        }
        
        vm.selectTask = function(value){
            vm.activeTask = value;
            loadComments(value);
        };

        vm.isActive = function(value){
            if(vm.activeTask == value){
                return true;
            }
            else {
                return false;
            }
        };

        vm.saveTask = function(isEdit){
            if (isEdit){
                DataService.setActiveTask(vm.activeTask);
            }
            $uibModal.open({
                templateUrl: 'app/app-modal/task.adaugare.html',
                controller: 'AddTaskController',
                controllerAs: 'vm'
            }).result.then(function(){
                loadTasks();
            }, function(res){})
        };

        vm.sendComment = function(){
            vm.newComment.task={};
            vm.newComment.task.id = vm.activeTask;
            vm.newComment.user={};
            vm.newComment.user.id = $rootScope.globals.currentUser.id;

            UserService.sendComment(vm.newComment).then(function(response){
                loadComments(vm.activeTask);
                vm.newComment={};
            });            
        }

        function loadComments(idTask){
            UserService.loadCommentsByTask(idTask).then(function(response){
                vm.activeComments = response;
            }); 
        }

        vm.exportaDetalii = function exportaDetalii(){
            var deferred = $q.defer();
            var defaultFileName='RaportTask.xlsx';
            vm.filtru.deptId = $rootScope.globals.currentUser.dept;
            $http.post('/api/taskReport', JSON.stringify(vm.filtru), {responseType: "arraybuffer" }).then(
                function (response) {
                    var type = response.headers('Content-Type');
                    var disposition = response.headers('Content-Disposition');
                    if (disposition) {
                        var match = disposition.match(/.*filename=\"?([^;\"]+)\"?.*/);
                        if (match[1])
                            defaultFileName = match[1];
                    }
                    defaultFileName = defaultFileName.replace(/[<>:"\/\\|?*]+/g, '_');
                    var blob = new Blob([response.data], { type: type });
                    saveAs(blob, defaultFileName);
                    deferred.resolve(defaultFileName);
                }, function (data, status) {
                    var e = /* error */
                        deferred.reject(e);
                });
            return deferred.promise;
        }

    }

})();