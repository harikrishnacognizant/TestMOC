<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<%@ page session="false"%>
<tags:template>
	<jsp:attribute name="breadcrumb">Home</jsp:attribute>

	<jsp:body>
		<c:url var="startProcess" value="/startProcess" />
		<table border="0">		
		<tr>
		  <td align="center"><input type="button"
					value="Step 1: Load Data" onclick="startDataLoading()" />
					
			  	
		    <input type="button" value="Step 2: Start Process"
					onclick="startMDProcess()" />
			</td>
			<td align="center">	
		    	<div dojoType="dijit.ProgressBar" style="width: 300px"
						jsId="jsProgress" id="downloadProgress" maximum="100">
		    
				
				
				
				</td>
		       		    		    		    
		</tr>
		<tr><td colspan="2" height="10px"></td></tr>
		<tr>		  	
		    <td><div id="clusteredColumnChart"
						style="width: 468px; height: 500px;"></div>
		        <div id="clusteredColumnLegend"></div>
		    </td>
		    <td><div id="pieChart" style="width: 468px; height: 500px;"></div>
		    <div id="pieLegend"></div>
		    </td>        		    		    		    
		</tr>
		<tr>
		<td align="center">
		 <b>Data variation vs. execution</b> 
		</td>
		<td align="center">
		<b>Overall Data Quality</b>
		</td>		
		</tr>		
		</table>
        <br />
	</jsp:body>
</tags:template>