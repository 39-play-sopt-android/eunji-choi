package org.sopt.play.presentation.post.model

val dummyPosts = listOf(
    PostCardUiModel(
        title = "오늘 첫 세미나 후기",
        content = "Compose로 화면 그리는 게 생각보다 재밌었어요.",
        author = "최은지",
        createdAt = "2026.11.26",
    ),
    PostCardUiModel(
        title = "LazyColumn 스크롤이 버벅여요",
        content = "아이템이 많아지니까 스크롤할 때 끊기는 느낌이 있는데 key를 주면 나아질까요?",
        author = "김솝트",
        createdAt = "2026.11.26",
    ),
    PostCardUiModel(
        title = "비 오는 날 듣기 좋은 노래",
        content = "잔잔한 노래 위주로 플레이리스트 만들어봤어요. 공부할 때 틀어두기 좋습니다.",
        author = "이플레이",
        createdAt = "2026.11.25",
    ),
    PostCardUiModel(
        title = "짧은 글",
        content = "짧다",
        author = "박안드",
        createdAt = "2026.11.25",
    ),
    PostCardUiModel(
        title = "ViewModel에서 State 관리하는 방법 정리",
        content = "StateFlow로 UiState를 하나로 묶고, Intent를 받아서 update로 copy하는 방식으로 정리해봤습니다. " +
                "처음엔 헷갈렸는데 흐름이 한 방향이라 디버깅할 때 훨씬 편하더라고요. " +
                "다음엔 SideEffect를 어떻게 처리할지 정리해보려고 합니다.",
        author = "정컴포즈",
        createdAt = "2026.11.24",
    ),
    PostCardUiModel(
        title = "스터디 같이 하실 분",
        content = "주 2회 온라인으로 안드로이드 스터디 하실 분 구해요!",
        author = "한스터디",
        createdAt = "2026.11.23",
    ),
    PostCardUiModel(
        title = "제목이 아주 길어지면 카드에서 어떻게 보이는지 확인하기 위한 테스트용 게시글 제목",
        content = "제목 말줄임 확인용입니다.",
        author = "테스트",
        createdAt = "2026.11.22",
    ),
    PostCardUiModel(
        title = "Room 써보신 분?",
        content = "게시글을 로컬에 저장하려고 하는데 Room이랑 DataStore 중에 뭐가 맞을까요?",
        author = "오데이터",
        createdAt = "2026.11.21",
    ),
    PostCardUiModel(
        title = "주말 추천 카페",
        content = "작업하기 좋은 카페 추천해요. 콘센트 많고 조용합니다.",
        author = "윤카페",
        createdAt = "2026.11.20",
    ),
    PostCardUiModel(
        title = "4주차 과제 끝!",
        content = "바텀시트랑 모달까지 다 붙였다. 이제 리팩터링만 남았어요.",
        author = "강과제",
        createdAt = "2026.11.19",
    ),
)
