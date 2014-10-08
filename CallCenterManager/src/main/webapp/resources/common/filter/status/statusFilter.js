Filters.filter('unique', function () {

  return function (items, filterOn) {

    if (filterOn === false) {
      return items;
    }

    if ((filterOn || angular.isUndefined(filterOn)) && angular.isArray(items)) {
      var hashCheck = {}, newItems = [];

      var extractValueToCompare = function (item) {
        if (angular.isObject(item) && angular.isString(filterOn)) {
          return item[filterOn];
        } else {
          return item;
        }
      };

      angular.forEach(items, function (item) {
        var valueToCheck, isDuplicate = false;

        for (var i = 0; i < newItems.length; i++) {
          if (angular.equals(extractValueToCompare(newItems[i]), extractValueToCompare(item))) {
            isDuplicate = true;
            break;
          }
        }
        if (!isDuplicate) {
          newItems.push(item);
        }

      });
      items = newItems;
    }
    return items;
  };
});

Filters.filter('status', function() {
  var filter = function(arr, filterStatus) {
    if (!arr) { return; }
    if (!filterStatus) { return arr; }
    var newArr = [];
    for (var i=0; i<arr.length; i++) {
      if (arr[i].status == filterStatus) {
          newArr.push(arr[i]);
        }
    }
    return newArr;
  };
  return filter;
});

Filters.filter('callcenter', function() {
  var filter = function(arr, callCenter, theStatus) {
    if (!arr || !callCenter || !theStatus) { return 0; }
    var count = 0;
    for (var i=0; i<arr.length; i++) {
      if (arr[i].callCenterId == callCenter && arr[i].status == theStatus) {
          count++;
        }
    }
    return count;
  };
  return filter;
});