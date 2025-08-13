package com.next.intune.user.repository;

import com.next.intune.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Long> {

    /**
     * 이메일과 유효 상태(Valid=true)에 해당하는 회원을 조회
     *
     * @param email 조회할 회원의 이메일
     * @return Optional<User>
     *         - 값이 존재하는 경우: 해당 이메일을 가진 유효 회원이 있음
     *         - 값이 비어있는 경우: 해당 이메일의 유효 회원이 없음
     */
    Optional<User> findByEmailAndValidTrue(String email);

    /**
     * 주어진 이메일이 이미 사용 중인지 확인 (유효 회원만 대상으로 함)
     *
     * @param email 중복 여부를 확인할 이메일
     * @return true  : 해당 이메일을 가진 유효 회원이 존재함 (중복)
     *         false : 사용 가능한 이메일
     */
    boolean existsByEmailAndValidTrue(String email);

    /**
     * 주어진 닉네임(name)이 이미 사용 중인지 확인 (유효 회원만 대상으로 함)
     *
     * @param name 중복 여부를 확인할 닉네임
     * @return true  : 해당 닉네임을 가진 유효 회원이 존재함 (중복)
     *         false : 사용 가능한 닉네임
     */
    boolean existsByNameAndValidTrue(String name);
}
