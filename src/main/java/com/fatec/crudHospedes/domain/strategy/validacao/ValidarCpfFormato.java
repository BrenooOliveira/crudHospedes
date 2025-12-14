package com.fatec.crudHospedes.domain.strategy.validacao;

import com.fatec.crudHospedes.domain.exception.BusinessException;
import com.fatec.crudHospedes.domain.model.HospedeModel;
import org.springframework.stereotype.Component;

@Component
public class ValidarCpfFormato implements IValidacaoStrategy<HospedeModel> {

    @Override
    public void validar(HospedeModel h) {

        String cpf = h.getCpf();

        if (cpf == null) {
            return; // obrigatoriedade já é validada em outra strategy
        }

        cpf = cpf.replaceAll("\\D", "");

        if (!isCpfValido(cpf)) {
            throw new BusinessException("CPF inválido.");
        }
    }

    private boolean isCpfValido(String cpf) {

        // Deve ter 11 dígitos
        if (cpf.length() != 11)
            return false;

        // Evita CPFs com todos os dígitos iguais
        if (cpf.matches("(\\d)\\1{10}"))
            return false;

        try {
            int soma = 0;
            int peso = 10;

            for (int i = 0; i < 9; i++) {
                soma += (cpf.charAt(i) - '0') * peso--;
            }

            int digito1 = 11 - (soma % 11);
            digito1 = (digito1 >= 10) ? 0 : digito1;

            if (digito1 != (cpf.charAt(9) - '0'))
                return false;

            soma = 0;
            peso = 11;

            for (int i = 0; i < 10; i++) {
                soma += (cpf.charAt(i) - '0') * peso--;
            }

            int digito2 = 11 - (soma % 11);
            digito2 = (digito2 >= 10) ? 0 : digito2;

            return digito2 == (cpf.charAt(10) - '0');

        } catch (Exception e) {
            return false;
        }
    }
}
