package com.fatec.crudHospedes.domain.strategy.validacao;

import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.fatec.crudHospedes.domain.exception.BusinessException;
import com.fatec.crudHospedes.domain.model.Endereco;
import com.fatec.crudHospedes.domain.model.HospedeModel;

@Component
public class ValidarEnderecoHospede implements IValidacaoStrategy<HospedeModel> {

    @Override
    public void validar(HospedeModel hospede) {

        Endereco e = hospede.getEndereco();

        if (e == null)
            throw new BusinessException("Endereço é obrigatório.");

        if (!StringUtils.hasText(e.getLogradouro()))
            throw new BusinessException("Logradouro é obrigatório.");

        if (!StringUtils.hasText(e.getNumero()))
            throw new BusinessException("Número é obrigatório.");

        if (!StringUtils.hasText(e.getCep()))
            throw new BusinessException("CEP é obrigatório.");

        if (!StringUtils.hasText(e.getBairro()))
            throw new BusinessException("Bairro é obrigatório.");

        if (!StringUtils.hasText(e.getCidade()))
            throw new BusinessException("Cidade é obrigatória.");

        if (!StringUtils.hasText(e.getEstado()))
            throw new BusinessException("Estado é obrigatório.");
    }
}
