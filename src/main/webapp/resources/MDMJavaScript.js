/**
 * 
 */
// Dependencies
dojo.require("dijit.ProgressBar");
dojo.require("dojo.parser");
dojo.require("dojox.charting.Chart2D");
dojo.require("dojox.charting.themes.Claro");
dojo.require("dojo.store.Observable");
dojo.require("dojo.store.Memory");
dojo.require("dojox.charting.StoreSeries");
dojo.require("dojox.charting.themes.MiamiNice");
dojo.require("dojox.charting.widget.Legend");
dojo.require("dojox.charting.action2d.Tooltip");
dojo.require("dojox.charting.action2d.Magnify");
dojo.require("dojox.charting.action2d.Highlight");
dojo.require("dojox.charting.themes.PlotKit.orange");
dojo.require("dojox.charting.themes.Shrooms");
dojo.require("dojox.charting.plot2d.Pie");
dojo.require("dojox.charting.action2d.MoveSlice");
dojo.require("dojox.charting.themes.Dollar");

// Global variables
var numberOfRecPropcessed1 = 0;
var numberOfRecPropcessed2 = 0;
var numberOfRecPropcessed3 = 0;
var numberOfRecPropcessed4 = 0;
var scale = 0;

// 60 mininutes is the estimated time to complete this process
var timeToComplete = 60;
var xAxisLabel = " min";

// Global variables for the chart
var totalNumberOfRecordsProcessed = 0;
var totalNumberOfGoldenRecords = 0;
var totalNumberOfNoMatchRecords = 0;
var totalNumberOfExactMatchRecords = 0;
var totalNumberOfPOTMatchRecords = 0;

var goldenRecord = "Golden Copy";
var noMatchRecord = "No Match";
var exactMatchRecord = "Exact Match";
var potMatchRecord = "POT Match";

var firstTime = true;
var needToGetProcessUpdate = true;

// When resources are loaded and the DOM is ready....
dojo.ready(function() {

	// getScale();

	displayPieChart();

	displayClusteredColumns();

	// displayLineGraph();
});

function startDataLoading() {
     // alert('Hello MDM On Cloud');
	dojo
			.xhrGet({
				// The following URL must match the destination
				// Use this before deploying
				// http://webcomponentdemo13.alpha.vmforce.com
				// Use this before deploying on local host
				// http://localhost:28093/webcomponent/
				// url:
				// "http://webcomponentdemo13.alpha.vmforce.com/startProcess",
				url : "http://localhost:9090/webcomponent/loadData",
				//url : "http://mdmoncloud.alpha.vmforce.com/loadData",
				handleAs : "text",
				timeout : 300000, // Time in milliseconds

				// The LOAD function will be called on a successful response.
				load : function(response, ioArgs) { //
					location.reload(true);
					alert("Data loading completed successfully");
					
					return response; // 
				},

				// The ERROR function will be called in an error case.
				error : function(response, ioArgs) { // 
					console.error("HTTP status code: ", ioArgs.xhr.status); //
					dojo.byId("replace").innerHTML = 'Invoking the server resource failed'; //  
					return response; // 
				},

				// Here you put the parameters to the server side program
				// We send two hard-coded parameters
				content : {
					name : "lars",
					url : "testing"
				}
			});

}

function getScale() {

	dojo
			.xhrGet({
				// The following URL must match the destination
				// url:
				// "http://webcomponentdemo13.alpha.vmforce.com/getHistoricalRecCount",
				// http://mdmoncloud.alpha.vmforce.com/ for vmforce
				url : "http://localhost:9090/webcomponent/getStartUpData",
				//url : "http://mdmoncloud.alpha.vmforce.com/getStartUpData",
				handleAs : "text",
				timeout : 5000, // Time in milliseconds

				// The LOAD function will be called on a successful response.
				load : function(response, ioArgs) { //
					serverResponse = response;
					// alert("serverResponse "+serverResponse);
					statArray = serverResponse;
					// alert("statArray "+statArray);
					scale = statArray[0] * 1;
					alert("Scale received " + scale);

					return response; // 
				},

				// The ERROR function will be called in an error case.
				error : function(response, ioArgs) { // 
					console.error("HTTP status code: ", ioArgs.xhr.status); //
					dojo.byId("replace").innerHTML = 'Loading the ressource from the server has failed'; //  
					return response; // 
				},

				// Here you put the parameters to the server side program
				// We send two hard-coded parameters
				content : {
					name : "lars",
					url : "testing"
				}
			});

}

function displayLineGraph() {

	// Initial data
	var data = [
	// This information, presumably, would come from a database or web service
	{
		id : 1,
		value : 0,
		recType1 : 1
	} ];

	// Create the data store
	// Store information in a data store on the client side
	var store = dojo.store.Observable(new dojo.store.Memory({
		data : {
			identifier : "id",
			label : "Records processed",
			items : data
		}
	}));

	// Create the chart within it's "holding" node
	// Global so users can hit it from the console
	chart = new dojox.charting.Chart2D("lineGraphShowingProgress");

	// Set the theme
	chart.setTheme(dojox.charting.themes.Claro);

	// Add the only/default plot
	chart.addPlot("default", {
		type : "Lines",
		markers : true
	});

	// Add axes
	chart.addAxis("x", {
		microTickStep : 1,
		minorTickStep : 1,
		max : scale
	});
	chart.addAxis("y", {
		vertical : true,
		fixLower : "major",
		fixUpper : "major",
		minorTickStep : 1
	});

	// Add the storeseries - Query for all data
	chart.addSeries("y", new dojox.charting.StoreSeries(store, {
		query : {
			recType1 : 1
		}
	}, "value"));
	// chart.addSeries("y2", new dojox.charting.StoreSeries(store, { query: {
	// site: 2 } }, "value"));

	// Render the chart!
	chart.render();

	// Simulate a data chage from a store or service
	var startNumber = data.length;
	var interval = setInterval(
			function() {

				dojo
						.xhrGet({
							// The following URL must match the destination
							// url:
							// "http://webcomponentdemo13.alpha.vmforce.com/getHistoricalRecCount",
							// http://mdmoncloud.alpha.vmforce.com/ for vmforce
							url : "http://localhost:9090/webcomponent/getHistoricalRecCount",
							//url : "http://mdmoncloud.alpha.vmforce.com/getHistoricalRecCount",
							handleAs : "text",
							timeout : 5000, // Time in milliseconds

							// The LOAD function will be called on a successful
							// response.
							load : function(response, ioArgs) { //
								serverResponse = response;
								// alert("serverResponse "+serverResponse);
								statArray = serverResponse * 1;
								// alert("statArray "+statArray);
								// scale = statArray[0];
								// alert("Scale received "+scale);
								numberOfRecPropcessed1 = statArray;

								// store.notify({ value: numberOfRecPropcessed1,
								// id: ++startNumber, recType1: 1 });

								return response; // 
							},

							// The ERROR function will be called in an error
							// case.
							error : function(response, ioArgs) { // 
								console.error("HTTP status code: ",
										ioArgs.xhr.status); //
								dojo.byId("replace").innerHTML = 'Loading the ressource from the server has failed'; //  
								return response; // 
							},

							// Here you put the parameters to the server side
							// program
							// We send two hard-coded parameters
							content : {
								name : "lars",
								url : "testing"
							}
						});

				// Notify the store of a data change
				store.notify({
					value : numberOfRecPropcessed1,
					id : ++startNumber,
					recType1 : 1
				});
				// store.notify({ value: Math.ceil(Math.random()*29), id:
				// ++startNumber, site: 2 });
				// Stop at 50
				if (startNumber == 20) {
					clearInterval(interval);
					// Refresh the page to update the ClusteredColumn & Pie
					// chart
					// location.reload(true);
					// displayPieChart();
					// displayClusteredColumns();
				}
			}, 1000);
}

function displayPieChart() {

	dojo
			.xhrGet({
				// The following URL must match the destination
				// url:
				// "http://webcomponentdemo13.alpha.vmforce.com/getHistoricalRecCount",
				url : "http://localhost:9090/webcomponent/getPieChartData",
				//url : "http://mdmoncloud.alpha.vmforce.com/getPieChartData",
				handleAs : "text",
				timeout : 5000, // Time in milliseconds timeout : 5000000

				// The LOAD function will be called on a successful response.
				load : function(response, ioArgs) { //
					serverResponse = response;
					// alert("serverResponse "+serverResponse);
					statArray = serverResponse.split("|");
					totalNumberOfGoldenRecords = statArray[0];
					// alert("gc"+totalNumberOfGoldenRecords);
					totalNumberOfPOTMatchRecords = statArray[1];
					// alert("pm"+totalNumberOfPOTMatchRecords);
					totalNumberOfExactMatchRecords = statArray[2];
					// alert("em"+totalNumberOfExactMatchRecords);

					/***/

					var data = [ totalNumberOfGoldenRecords,
							totalNumberOfPOTMatchRecords,
							totalNumberOfExactMatchRecords ];
					// Make an ajax call to get an update for displaying pie
					// chart

					// Pie chart
					var pieChart = new dojox.charting.Chart2D("pieChart");
					pieChart.setTheme(dojox.charting.themes.Claro);

					pieChart.addPlot("default", {
						type : "Pie",
						labelOffset : -25,
						font : "Veranda",
						radius : 110,
						labelStyle : "columns"

					});

					new dojox.charting.action2d.MoveSlice(pieChart, "default");
					new dojox.charting.action2d.Highlight(pieChart, "default");
					new dojox.charting.action2d.Tooltip(pieChart, "default");

					var pieData = [];
					var total = 0;
					var key = "";
					var pieIndex = 1;
					var legendArrIndx = 0;
					var label = "";
					var legendArr = [ "Golden Copy", "POT Match", "Exact Match" ];
					for (key in data) {
						label = pieIndex;
						label += " : ";
						label += data[key];
						label += "%";
						if (data[key] != 0) {
							pieData.push({
								x : pieIndex,
								y : data[key],
								legend : legendArr[key]
							});
						}
						// t++;
						// alert("key"+key);
						// legendArrIndx ++;
					}

					// add the data series
					pieChart.addSeries("ClassificationOfData", pieData);
					// slice animation!
					new dojox.charting.action2d.MoveSlice(pieChart, "default");
					// tooltips!
					new dojox.charting.action2d.Tooltip(pieChart, "default");
					// render the chart
					pieChart.render();
					// render the legend
					var legend = new dojox.charting.widget.Legend({
						chart : pieChart
					}, "pieLegend");

					/***/

					return response; // 
				},

				// The ERROR function will be called in an error case.
				error : function(response, ioArgs) { // 
					console.error("HTTP status code: ", ioArgs.xhr.status); //
					dojo.byId("replace").innerHTML = 'Loading the ressource from the server has failed'; //  
					return response; // 
				},

				// Here you put the parameters to the server side program
				// We send two hard-coded parameters
				content : {
					name : "lars",
					url : "testing"
				}
			});
}

function displayClusteredColumns() {

	var vArr1 = new Array();
	var vArr2 = new Array();
	var vArr3 = new Array();
	var vArr4 = new Array();

	dojo
			.xhrGet({
				// The following URL must match the destination
				// url:
				// "http://webcomponentdemo13.alpha.vmforce.com/getHistoricalRecCount",
				url : "http://localhost:9090/webcomponent/getClusteredColumnsData",
				//url : "http://mdmoncloud.alpha.vmforce.com/getClusteredColumnsData",
				handleAs : "text",
				timeout : 5000, // Time in milliseconds

				// The LOAD function will be called on a successful response.
				load : function(response, ioArgs) { //
					serverResponse = response;
					statArray = serverResponse.split("|");
					var clusteredColumnChart = new dojox.charting.Chart2D(
							"clusteredColumnChart");
					clusteredColumnChart.addPlot("default", {
						type : "ClusteredColumns",
						gap : 5,
						markers : true
					});
					clusteredColumnChart.addPlot("horzgrid", {
						type : "Grid",
						hMajorLines : true,
						vMajorLines : false
					});
					clusteredColumnChart.addAxis("x", {
						minorTicks : false,
						microTicks : false
					});
					// Set the theme
					clusteredColumnChart.setTheme(dojox.charting.themes.Claro);
					// Set y axis
					clusteredColumnChart.addAxis("y", {
						vertical : true,
						min : 0,
						max : 100,
						majorTickStep : 5,
						minorTickStep : 1,
						stroke : "grey",
						majorTick : {
							stroke : "red",
							length : 4
						},
						minorTick : {
							stroke : "gray",
							length : 2
						}
					});

					for (i = 0; i < 12; i++) {

						v1 = statArray[i] * 1;
						v2 = statArray[++i] * 1;
						v3 = statArray[++i] * 1;
						v4 = statArray[++i] * 1;

						if (i == 3) {
							vArr1 = [ v1, v2, v3, v4 ];
							// alert("vArr1"+vArr1);
						}
						if (i == 7) {
							vArr2 = [ v1, v2, v3, v4 ];
							// alert("vArr2"+vArr2);
						}
						if (i == 11) {
							vArr3 = [ v1, v2, v3, v4 ];
							// alert("vArr3"+vArr3);
						}
						// if (i == 11) {
						// vArr4 = [ v1, v2, v3 , v4 ];
						// alert("vArr4"+vArr4);
						// }
					}

					clusteredColumnChart.addSeries(goldenRecord, vArr1, {
						fill : "#ddddff"
					});
					clusteredColumnChart.addSeries(potMatchRecord, vArr2, {
						fill : "#FEFFBF"
					});
					clusteredColumnChart.addSeries(exactMatchRecord, vArr3, {
						fill : "#CFFFD1"
					});
					// clusteredColumnChart.addSeries(potMatchRecord, vArr4, {
					// fill : "#DFD2C3"
					// });

					// Create the tooltip
					var tip = new dojox.charting.action2d.Tooltip(
							clusteredColumnChart, "default");

					// Create the magnifier
					var mag = new dojox.charting.action2d.Magnify(
							clusteredColumnChart, "default");

					// Highlight!
					new dojox.charting.action2d.Highlight(clusteredColumnChart,
							"default");

					clusteredColumnChart.render();

					var legend = new dojox.charting.widget.Legend({
						chart : clusteredColumnChart
					}, "clusteredColumnLegend");

					return response; // 
				},

				// The ERROR function will be called in an error case.
				error : function(response, ioArgs) { // 
					console.error("HTTP status code: ", ioArgs.xhr.status); //
					dojo.byId("replace").innerHTML = 'Loading the ressource from the server has failed'; //  
					return response; // 
				},

				// Here you put the parameters to the server side program
				// We send two hard-coded parameters
				content : {
					name : "lars",
					url : "testing"
				}
			});
}

// Timer services
var c = 0;
var t;
var timer_is_on = 0;

function startMDProcessOnTimer() {
	// if (needToGetProcessUpdate) {
	// alert("Hello there");
	getProcessUpdate();
	// }
	// else{
	// alert("needToGetProcessUpdate "+needToGetProcessUpdate);
	// }
	// The progress bar refreshes itself after every 5 seconds
	t = setTimeout("startMDProcessOnTimer()", 5000); // t =
														// setTimeout("startMDProcessOnTimer()",
														// 5555555555000);
}

function startHistoricalRecCountOnTimer() {
	getHistoricalRecCount();
	// The progress bar refreshes itself after every 5 seconds
	t = setTimeout("startHistoricalRecCountOnTimer()", 5000);
}

function startMDProcess() {
	// Start the process
	startProcess();
	// Timer is initialised
	if (!timer_is_on) {
		timer_is_on = 1;
		startMDProcessOnTimer();
		// startHistoricalRecCountOnTimer();
	}
}

function startProcess() {

	dojo
			.xhrGet({
				// The following URL must match the destination
				// Use this before deploying
				// http://webcomponentdemo13.alpha.vmforce.com
				// Use this before deploying on local host
				// http://localhost:28093/webcomponent/
				// url:
				// "http://webcomponentdemo13.alpha.vmforce.com/startProcess",
				url : "http://localhost:9090/webcomponent/startProcess",
				//url : "http://mdmoncloud.alpha.vmforce.com/startProcess",

				handleAs : "text",
				timeout : 5000, // Time in milliseconds

				// The LOAD function will be called on a successful response.
				load : function(response, ioArgs) { //		            		          		          
					alert("Has the process started " + response);
					return response; // 
				},

				// The ERROR function will be called in an error case.
				error : function(response, ioArgs) { // 
					console.error("HTTP status code: ", ioArgs.xhr.status); //
					dojo.byId("replace").innerHTML = 'Invoking the server resource failed'; //  
					return response; // 
				},

				// Here you put the parameters to the server side program
				// We send two hard-coded parameters
				content : {
					name : "lars",
					url : "testing"
				}
			});
	// displayLineGraph();
}

// Process update : This function will update the progress bar
function getProcessUpdate() {

	dojo
			.xhrGet({
				// The following URL must match the destination
				// url:
				// "http://webcomponentdemo13.alpha.vmforce.com/getProcessUpdate",
				url : "http://localhost:9090/webcomponent/getProcessUpdate",
				//url : "http://mdmoncloud.alpha.vmforce.com/getProcessUpdate",
				handleAs : "text",
				timeout : 5000, // Time in milliseconds

				// The LOAD function will be called on a successful response.
				load : function(response, ioArgs) { //
					var updatedVal = response * 1;
					// alert("updatedVal"+updatedVal);
					jsProgress.update({
						maximum : 100,
						progress : updatedVal
					});
					if (updatedVal == 100) {

						// needToGetProcessUpdate = false;
						confirm("Page refresh will happen. Please confirm?");
						location.reload(true);
					}
					return response; // 
				},

				// The ERROR function will be called in an error case.
				error : function(response, ioArgs) { // 
					console.error("HTTP status code: ", ioArgs.xhr.status); //
					dojo.byId("replace").innerHTML = 'Loading the ressource from the server has failed'; //  
					return response; // 
				},

				// Here you put the parameters to the server side program
				// We send two hard-coded parameters
				content : {
					name : "lars",
					url : "testing"
				}
			});

}
// Charting update : This function will get update on the number of previously
// processed records
function getHistoricalRecCount() {

	dojo
			.xhrGet({
				// The following URL must match the destination
				// url:
				// "http://webcomponentdemo13.alpha.vmforce.com/getHistoricalRecCount",
				// url :
				// "http://localhost:28093/webcomponent/getHistoricalRecCount",
				url : "http://localhost:28093/webcomponent/getHistoricalRecCount",
				//url : "http://mdmoncloud.alpha.vmforce.com/getHistoricalRecCount",
				handleAs : "text",
				timeout : 5000, // Time in milliseconds

				// The LOAD function will be called on a successful response.
				load : function(response, ioArgs) { //
					serverResponse = response;
					// alert("serverResponse "+serverResponse);
					statArray = serverResponse.split("|");
					// alert("statArray "+statArray);
					numberOfRecPropcessed1 = statArray[0];
					numberOfRecPropcessed2 = statArray[1];
					numberOfRecPropcessed3 = statArray[2];
					numberOfRecPropcessed4 = statArray[3];
					return response; // 
				},

				// The ERROR function will be called in an error case.
				error : function(response, ioArgs) { // 
					console.error("HTTP status code: ", ioArgs.xhr.status); //
					dojo.byId("replace").innerHTML = 'Loading the ressource from the server has failed'; //  
					return response; // 
				},

				// Here you put the parameters to the server side program
				// We send two hard-coded parameters
				content : {
					name : "lars",
					url : "testing"
				}
			});

}

// Table update : Number of records processed Vs. Time
function getTableUpdate() {

}
function setUp() {
	// Does nothing
}