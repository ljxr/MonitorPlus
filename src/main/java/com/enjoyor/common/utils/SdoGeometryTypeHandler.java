package com.enjoyor.common.utils;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

import oracle.spatial.geometry.JGeometry;
import oracle.sql.STRUCT;
 
import org.apache.commons.dbcp.DelegatingPreparedStatement;
import org.apache.commons.dbcp.PoolableConnection; 
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedJdbcTypes;
import org.apache.ibatis.type.MappedTypes;
import org.apache.ibatis.type.TypeHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.druid.pool.DruidPooledPreparedStatement; 
 
@MappedTypes({JGeometry.class})
@MappedJdbcTypes({JdbcType.STRUCT})//这两个Mapped也可不需要
public class SdoGeometryTypeHandler implements TypeHandler<JGeometry> {
	private Logger logger = LoggerFactory.getLogger(SdoGeometryTypeHandler.class);

    @Override
    public void setParameter(PreparedStatement ps, int i, JGeometry parameter,
            JdbcType jdbcType) throws SQLException {
    	try{
    		Connection conn = ps.getConnection();
    		if(ps instanceof DelegatingPreparedStatement){
    			DelegatingPreparedStatement dps = (DelegatingPreparedStatement)((DelegatingPreparedStatement)ps).getDelegate();
     			conn = dps.getConnection();
    		}
	    	if(conn instanceof PoolableConnection){
	    		conn = ((PoolableConnection) conn).getInnermostDelegate();
	    		
	    	}	
	    	if(ps instanceof DruidPooledPreparedStatement){
	    		 PreparedStatement dps= (PreparedStatement)((DruidPooledPreparedStatement)ps).getRawPreparedStatement();
		    	 conn=dps.getConnection(); 
	    	}

	    	 
	    	 
	    	if(parameter!=null){
	    	    STRUCT dbObject = JGeometry.store(parameter, conn);	 	        
	 	        ps.setObject(i, dbObject);
	    	}else{
	    	   //  ps.setObject(i, null);
	    	     ps.setNull(i, Types.STRUCT);
	    	}
	   
    	}catch(Exception ex){
    		logger.error("JGeometry类型转换失败！！",ex);
    	}
    }

    @Override
    public JGeometry getResult(ResultSet rs, String columnName)
            throws SQLException {
        // TODO Auto-generated method stub
        STRUCT st = (STRUCT) rs.getObject(columnName);
        if (st != null) {
            return JGeometry.load(st);
        }
        return null;
    }

    @Override
    public JGeometry getResult(ResultSet rs, int columnIndex)
            throws SQLException {
        // TODO Auto-generated method stub
        STRUCT st = (STRUCT) rs.getObject(columnIndex);
        if (st != null) {
            return JGeometry.load(st);
        }
        return null;
    }

    @Override
    public JGeometry getResult(CallableStatement cs, int columnIndex)
            throws SQLException {
        // TODO Auto-generated method stub
        STRUCT st = (STRUCT) cs.getObject(columnIndex);
        if (st != null) {
            return JGeometry.load(st);
        }
        return null;
    }
}