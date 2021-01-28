package com.koscom.stockox.mapper;

import com.koscom.stockox.dto.*;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface KoscomMapper {
    /**
     * 1. 대상 테이블 : company_info
     * 2. 관련 내용 : 모든 회사에 대한 회사코드 및 회사명을 가져온다.
     */
    @Select("SELECT company_id,company_name FROM company_info")
    List<CompanyInfo> findAllCompany();
    /**
     * 1. 대상 테이블 : company_info
     * 2. 관련 내용 : 회사명칭과 회사별 회사코드를 company_info 테이블에 삽입하는 쿼리
     */
    @Insert("INSERT INTO company_info (company_id, company_name) VALUES (#{companyId}, #{companyName})")
    void insertCompanyInfo(CompanyInfo companyInfo);
    /**
     * 1. 대상 테이블 : company_price_info
     * 2. 관련 내용 : company_price_info에 시가 저가 고가 (종가 제외)한 데이터를 삽입한다.
     *  > 종가에 대한 api가 별도로 존재하기에 우선은 종가에 대한 가격은 11111로 insert
     *  > 종가에 대한 api를 이용해 company_price_info 테이블의 종가 가격을 update 예정
     */
    @Insert("INSERT INTO company_price_info (company_id, company_start_price,company_end_price,company_min_price,company_max_price) " +
            "VALUES (#{companyId}, #{companyStartPrice}, #{companyEndPrice},#{companyMinPrice},#{companyMaxPrice})")
    void insertCompanyPriceInfo(CompanyPriceInfo companyPriceInfo);

    /**
     * 1. 대상 테이블 : kospi_info
     * 2. 관련 내용 : kospi_info에 kospi의 시가 고가 저가 체결가 데이터를 삽입한다.
     */
    @Insert("INSERT INTO kospi_info (start_price, max_price,min_price,end_price) " +
            "VALUES (#{startPrice}, #{maxPrice}, #{minPrice},#{tradePrice})")
    void insertKospiInfo(KospiInfo kospiInfo);


    /**
     * 1. 대상 테이블 : kosdaq_info
     * 2. 관련 내용 : kosdaq_info에 kosdaq의 시가 고가 저가 체결가 데이터를 삽입한다.
     */
    @Insert("INSERT INTO kosdaq_info (start_price, max_price,min_price,end_price) " +
            "VALUES (#{startPrice}, #{maxPrice}, #{minPrice},#{tradePrice})")
    void insertKosdaqInfo(KosdaqInfo kosdaqInfo);

    /**
     * 1. 대상 테이블 : company_price_info
     * 2. 관련 내용 : 가격이 존재하는 회사인지 아닌지에 대한 정보 얻기 위한 select 문
     */
    @Select("SELECT count(*) from company_price_info where company_id =#{company_id}")
    int isExistedPrice(@Param("company_id") String company_id);

    /**
     * 1. 대상 테이블 : company_price_info
     * 2. 관련 내용 : 해당 테이블의 start_price 값을 가져온다.
     */
    @Select("SELECT company_start_price FROM company_price_info where company_id =#{company_id}")
    Double getStartPrice(@Param("company_id") String company_id) ;

    /**
     * 1. 대상 테이블 : company_price_info
     * 2. 관련 내용 : 해당 테이블의 end_price 값을 가져온다.
     */
    @Select("SELECT company_end_price FROM company_price_info where company_id =#{company_id}")
    Double getEndPrice(@Param("company_id") String company_id) ;

    /**
     * 1. 대상 테이블 : company_price_info
     * 2. 관련 내용 : 해당 테이블의 min_price 값을 가져온다.
     */
    @Select("SELECT company_min_price FROM company_price_info where company_id =#{company_id}")
    Double getMinPrice(@Param("company_id") String company_id) ;

    /**
     * 1. 대상 테이블 : company_price_info
     * 2. 관련 내용 : 해당 테이블의 max_price 값을 가져온다.
     */
    @Select("SELECT company_max_price FROM company_price_info where company_id =#{company_id}")
    Double getMaxPrice(@Param("company_id") String company_id) ;
    /**
     * 1. 대상 테이블 : price_problem
     * 2. 관련 내용 : 각 회사에 대한 문제 ( 시가, 종가, 저가, 공가 관련) 데이터 테이블
     */
    @Insert("INSERT INTO price_bogi (company_id,flag,answer,wrong_answer1,wrong_answer2,wrong_answer3) " +
            "VALUES(#{companyId},#{flag},#{answer},#{wrongAnswer1},#{wrongAnswer2},#{wrongAnswer3})")
    void createPriceBogi(PriceBogi priceBogi);

    /**
     * 1.대상 테이블 : kospi_info
     * 2.관련 내용 : kospi 정보 받아옴.
     */
    @Select("SELECT start_price,max_price,min_price,end_price from kospi_info")
    KospiInfo getKospiInfo();


    /**
     * 1. 대상 테이블 : company_price_info
     * 2. 관련 내용 : 가격이 존재하는 회사인지 아닌지에 대한 정보 얻기 위한 select 문
     */
    @Select("SELECT count(*) from company_price_info where company_id =#{company_id}")
    int isExistedCompany(@Param("company_id") String company_id);
    /**
     * 1.대상 테이블 : kosdaq_info
     * 2.관련 내용 : kosdaq 정보 받아옴.
     */
    @Select("SELECT start_price,max_price,min_price,end_price from kosdaq_info")
    KosdaqInfo getKosdaqInfo();

    /**
     * 1. 대상 테이블 : price_bogi
     * 2. 관련 내용 : 가격에 대한 정답 및 오답이 존재하는 회사인지 확인.
     */
    @Select("SELECT count(*) FROM price_bogi " +
            "WHERE company_id =#{companyId}")
    int isExistedBogi(String companyId);

    /**
     * 1.대상 테이블 : price_bogi
     * 2.관련 내용 : 특정 회사에 대한 시가/저가/종가/고가와 관련 정답 및 오답 객체를 가져옴.
     */
    @Select("SELECT company_id,flag,answer,wrong_answer1,wrong_answer2,wrong_answer3 FROM price_bogi " +
            "WHERE company_id = #{company_id} AND flag = #{flag}")
    PriceBogi getPriceBogi(@Param("company_id") String company_id,@Param("flag") String flag);

    /**
     * 1.대상 테이블 : company_price_info
     * 2.관련 내용 : 회사 가격에 대한 정보를 가져옴.
     */
    @Select("SELECT company_id,company_start_price,company_end_price,company_min_price,company_max_price FROM company_price_info " +
            "where company_id=#{company_id}")
    CompanyPriceInfo getCompanyPriceInfo(String company_id);

    /**
     * 1. 대상 테이블 : problem
     * 2. 관련 내용 : 각 회사에 대한 문제 ( 시가, 종가, 저가, 공가 관련) 데이터 테이블
     */
    @Insert("INSERT INTO problem (company_id,problem,is_true,flag,idx) " +
            "VALUES(#{companyId},#{problem},#{isTrue},#{flag},#{index})")
    void createProblem(PriceProblem priceProblem);

    /**
     * 1. 대상 테이블 : problem
     * 2. 관련 내용 : 클라이언트 쪽에서 회사 선택 시에 문제 랜덤으로 가져오기 위한 select문
     */
    @Select("SELECT company_id ,problem, is_true, flag, idx " +
            "FROM problem " +
            "WHERE company_id = #{company_id} " +
            "AND flag = #{flag} " +
            "AND idx = #{idx}")
    PriceProblem returnProblem(@Param("company_id") String company_id,@Param("flag") String flag,@Param("idx") int idx);

    /**
     * 1.대상 테이블 : base_problem
     * 2.관련 내용 : 기본 문제 중에서 하나 가져오는 select 문
     */
    @Select("SELECT id,answer,wrong_answer FROM base_problem where id = #{id}")
    BaseProblem returnBaseProblem(int id);
    /**
     * 1.대상 테이블 : company_info
     * 2:관련 내용: 회사 정보에 대한 모든 데이터 삭제 > 배치 파일 실행을 위한 것
     */
    @Delete("DELETE FROM company_info")
    void deleteCompanyInfo();

    /**
     * 1.대상 테이블 : company_pricE_info
     * 2:관련 내용: 회사 가격 정보에 대한 모든 데이터 삭제 > 배치 파일 실행을 위한 것
     */
    @Delete("DELETE FROM company_price_info")
    void deleteCompanyPriceInfo();

    /**
     * 1.대상 테이블 : kospi_info
     * 2:관련 내용: kospi 정보에 대한 모든 데이터 삭제 > 배치 파일 실행을 위한 것
     */
    @Delete("DELETE FROM kospi_info")
    void deleteKospiInfo();

    /**
     * 1.대상 테이블 : kosdaq_info
     * 2:관련 내용: kospi 정보에 대한 모든 데이터 삭제 > 배치 파일 실행을 위한 것
     */
    @Delete("DELETE FROM kosdaq_info")
    void deleteKosdaqInfo();

    /**
     * 1.대상 테이블 : price_bogi
     * 2:관련 내용: 문제 보기에 대한 모든 데이터 삭제 > 배치 파일 실행을 위한 것
     */
    @Delete("DELETE FROM price_bogi")
    void deletePriceBogi();

    /**
     * 1.대상 테이블 : problem
     * 2:관련 내용: 문제에 대한 모든 데이터 삭제 > 배치 파일 실행을 위한 것
     */
    @Delete("DELETE FROM problem")
    void deleteProblem();

}
