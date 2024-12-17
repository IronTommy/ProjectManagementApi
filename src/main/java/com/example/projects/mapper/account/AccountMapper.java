package com.example.projects.mapper.account;

import com.example.projects.dto.account.AccountDto;
import com.example.projects.dto.account.AccountUpdateDto;
import com.example.projects.entity.account.Account;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(
        componentModel = "spring",
        nullValuePropertyMappingStrategy =
                NullValuePropertyMappingStrategy.IGNORE
        )
public interface AccountMapper {

    Account dtoToEntity(AccountDto dto);

    AccountDto entityToDto(Account account);

    void update(@MappingTarget Account account, AccountUpdateDto dto);
}
