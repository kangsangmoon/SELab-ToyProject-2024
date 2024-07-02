package com.example.project.service;

import com.example.project.dto.CodeResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Service
public class CodeResultService {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private final RowMapper<CodeResult> rowMapper = (rs, rowNum) -> {
        CodeResult codeResult = new CodeResult();
        codeResult.setId(rs.getLong("id"));
        codeResult.setOutput(rs.getString("output"));
        codeResult.setCorrect(rs.getBoolean("is_correct"));
        return codeResult;
    };

    public CodeResult save(CodeResult codeResult) {
        if (codeResult.getId() == null) {
            jdbcTemplate.update(
                    "INSERT INTO code_result (output, is_correct) VALUES (?, ?)",
                    codeResult.getOutput(), codeResult.isCorrect()
            );
            Long id = jdbcTemplate.queryForObject("SELECT LAST_INSERT_ID()", Long.class);
            codeResult.setId(id);
        } else {
            jdbcTemplate.update(
                    "UPDATE code_result SET output = ?, is_correct = ? WHERE id = ?",
                    codeResult.getOutput(), codeResult.isCorrect(), codeResult.getId()
            );
        }
        return codeResult;
    }

    public List<CodeResult> findAll() {
        return jdbcTemplate.query("SELECT * FROM code_result", rowMapper);
    }

    public CodeResult findById(Long id) {
        return jdbcTemplate.queryForObject("SELECT * FROM code_result WHERE id = ?", new Object[]{id}, rowMapper);
    }
}