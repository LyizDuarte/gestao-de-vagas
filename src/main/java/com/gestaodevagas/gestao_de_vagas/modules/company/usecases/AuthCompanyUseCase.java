package com.gestaodevagas.gestao_de_vagas.modules.company.usecases;

import javax.naming.AuthenticationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.gestaodevagas.gestao_de_vagas.modules.company.dto.AuthCompanyDTO;
import com.gestaodevagas.gestao_de_vagas.modules.company.repositories.CompanyRepository;

@Service
public class AuthCompanyUseCase {

    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public void execute(AuthCompanyDTO authCompanyDTO) {
        var company = this.companyRepository.findByUsername(authCompanyDTO.getUsername()).orElseThrow(() -> {
            throw new UsernameNotFoundException("User not found");
        });

        var passwordMatches = this.passwordEncoder.matches(authCompanyDTO.getPassword(), company.getPassword());

        if (!passwordMatches) {
            throw new AuthenticationException();
        }
    }
}
