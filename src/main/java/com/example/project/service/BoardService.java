package com.example.project.service;

import com.example.project.dto.board.BoardResponse;
import com.example.project.dto.board.request.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class BoardService {

    /*
    * TODO - register
    * BoardRegisterRequest를 인자로 받아 와서 데아터베이스에 정보를 넣고 생성한다.
    * 생성 후 생성된 테이블 정보를 BoardResponse에 답아서 보낸다
    * */
    @Transactional
    public BoardResponse register(BoardRegisterRequest request){

        return new BoardResponse();
    }


    /*
    * TODO - update
    * BoardUpdateRequest를 인자로 받는다.
    * 인자로 받은 후 userId와 boardId의 writerId가 같다면 수정을 할 수 있다.
    * 수정은 request에 있는 title과 context를 PATCH하는 방식으로 한다
    * 마지막에 board에 대한 정보를 BoardResponse에 담아서 리턴한다.
    * */
    @Transactional
    public BoardResponse update(BoardUpdateRequest request){

        return new BoardResponse();
    }


    /*
    * TODO - read
    * BoardReadRequest를 인자로 받는다
    * 인자로 받은 후 boardId를 가지고 데이터베이스에서 정보를 찾아서 board에 대한 정보를 BoardResponse에 담아서 리턴한다
    * */
    @Transactional
    public BoardResponse read(BoardReadRequest request){

        return new BoardResponse();
    }

    /*
    * TODO - readAll
    * BoardReadAllRequest를 받아서 그 SolutionID와 같은 모든 테이블을 가져올 수 있도록 만든다.
    * */
    @Transactional
    public List<BoardResponse> readAll(BoardReadAllRequest request){
        return new LinkedList<>();
    }

    /*
    * TODO - delete
    * BoardDeleteRequest를 인자로 받는다.
    * 만약 boardId의 writerId와 userId가 같다면 삭제를 허가한다.
    * 리턴값이 없다.
    * */
    @Transactional
    public void delete(BoardDeleteRequest request){

    }
}