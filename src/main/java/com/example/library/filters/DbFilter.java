package com.example.library.filters;

import com.example.library.utils.Db;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;

@WebFilter( "/*" )
public class DbFilter implements Filter {
    private FilterConfig filterConfig ;

    @Override
    public void init(FilterConfig filterConfig) {
        this.filterConfig = filterConfig ;
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws ServletException {
        //Db.setConnection(null);
        File config = new File(
                filterConfig
                        .getServletContext()
                        .getRealPath("WEB-INF/")
                        + "/" + "config.json");
        if (!config.exists()) {
            System.err.println("config/db.json not found");
        }
        else{
            JSONObject configData = null;
            try (InputStream reader = new FileInputStream(config)) {
                int len = (int) config.length();
                byte[] buf = new byte[len];
                if (len != reader.read(buf))
                    throw new IOException("File read integrity fails");
                configData = (JSONObject)
                        new JSONParser().parse(new String(buf));
                if (!Db.setConnection(configData))
                    throw new SQLException("Db connection error");
            }catch (Exception ex){
                System.err.println("DbFilter: " + ex.getMessage());
            }
        }
        try{
            // Checking connection to be opened
            if (Db.getConnection() == null) {
                // No connection - use static mode
                request.getRequestDispatcher("/WEB-INF/static.jsp").forward(request, response);
            } else {
                chain.doFilter(request, response);
                Db.closeConnection();
            }
        } catch (IOException ex) {
            System.err.println(ex.getMessage());
        }
    }

    @Override
    public void destroy() {this.filterConfig = null ;}
}
