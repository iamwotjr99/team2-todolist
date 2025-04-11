let currentFilter = "전체";
let rankingLikes = [0, 0, 0]; // 각 항목의 좋아요 수
let likedItems = {};  // 각 항목의 좋아요 상태

const token = localStorage.getItem("accessToken");
if (!token) {
  alert("비정상적인 접근입니다. 로그인이 필요합니다.");
  location.href = "../login.html";
}

// 로그아웃 기능
function logout() {
  localStorage.removeItem("accessToken");
  alert("로그아웃 되었습니다.");
  location.href = "../login.html";
}

// 나의 모든 할 일 조회
function fetchTodos() {
  fetch("http://52.64.175.1:8080/api/todo", {
    method: "GET",
    headers: {
      "Authorization": `Bearer ${token}`
    }
  })
  .then(response => response.json())
  .then(result => {
    console.log("불러온 할일 목록:", result);
    if (!Array.isArray(result.data)) {
      console.error("데이터 오류!", result);
      return;
    }
    renderTodos(result.data);
  });
}

// 새로고침시 할일 불러오기
window.onload = function() {
  fetchTodos();
  fetchOtherUsersRecentTodos("전체"); // 다른 사람의 최근 할일을 불러옵니다.
};

// 나의 할일 생성
function addTodo() {
  const input = document.getElementById("todoInput");
  const select = document.getElementById("todoCategory");
  const text = input.value.trim();
  const category = select.value;

  if (!text || !category) return alert("할일과 카테고리를 모두 입력해주세요!");

  fetch("http://52.64.175.1:8080/api/todo", {
    method: "POST",
    headers: {
      "Content-Type": "application/json",
      "Authorization": `Bearer ${token}`
    },
    body: JSON.stringify({ content: text, category })
  })
  .then(response => {
    if (!response.ok) throw new Error("요청 실패!");
    return response.json();
  })
  .then(data => {
    console.log("추가된 할일: ", data);
    input.value = "";
    select.value = "";
    fetchTodos();
  })
  .catch(err => {
    console.error("할일 추가중 오류 발생: ", err);
    alert("할 일 추가중 오류발생!");
  });
}

// 나의 할 일을 수정
function editTodo(id, oldText, category, done) {
  const newText = prompt("할 일을 수정하세요:", oldText);
  if (!newText || newText.trim() === "") return;

  fetch(`http://52.64.175.1:8080/api/todo/${id}`, {
    method: "PUT",
    headers: {
      "Content-Type": "application/json",
      "Authorization": `Bearer ${token}`
    },
    body: JSON.stringify({
      content: newText,
      category: category,
      done: done
    })
  })
  .then(response => {
    if (!response.ok) throw new Error("서버 응답 실패");
    return response.json();
  })
  .then(() => fetchTodos())
  .catch(error => {
    console.error("수정 오류 발생: ", error);
    alert("할 일 수정중 오류 발생!");
  });
}

// 나의 할 일을 삭제
function deleteTodo(id) {
  fetch(`http://52.64.175.1:8080/api/todo/${id}`, {
    method: "DELETE",
    headers: {
      "Authorization": `Bearer ${token}`
    }
  })
  .then(() => fetchTodos())
  .catch(error => {
    console.error("삭제 오류 발생: ", error);
    alert("할 일 삭제중 오류 발생!");
  });
}

// 할 일 완료 상태 토글
function toggleTodo(id, checked) {
  fetch(`http://52.64.175.1:8080/api/todo/${id}/toggle`, {
    method: "PATCH",
    headers: {
      "Content-Type": "application/json",
      "Authorization": `Bearer ${token}`
    },
    body: JSON.stringify({ done: checked })
  })
  .then(() => fetchTodos());
}

// 왼쪽 영역(투두리스트)의 카테고리 탭 기능
function filterTodos(category, clickedBtn) {
  currentFilter = category;
  fetchTodos(); // 현재 필터를 기준으로 나의 할 일을 다시 불러옴

  const buttons = document.querySelectorAll(".category-tab");
  buttons.forEach(btn => btn.classList.remove("active"));
  clickedBtn.classList.add("active");
}

// 오른쪽 영역(다른사용자 할일)의 카테고리 탭 기능
function filterRanking(category, clickedBtn) {
  const buttons = document.querySelectorAll(".ranking-tab");
  buttons.forEach(btn => btn.classList.remove("active"));
  clickedBtn.classList.add("active");

  fetchOtherUsersRecentTodos(category); // 선택된 카테고리로 다른 사용자의 할일을 불러옵니다.
}

// 투두리스트 화면 렌더링 기능들
function renderTodos(data) {
  const list = document.getElementById("todoList");
  list.innerHTML = "";

  const filtered = data.filter(todo => currentFilter === '전체' || todo.category === currentFilter);

  let doneCount = 0;

  filtered.forEach(todo => {
    if (todo.done) doneCount++;

    const li = document.createElement("li");
    li.innerHTML = `
      <div class="todo-left">
        <input type="checkbox" ${todo.done ? 'checked' : ''} onchange="toggleTodo(${todo.id}, this.checked)">
        <span style="text-decoration: ${todo.done ? 'line-through' : 'none'}">${todo.content || '내용없음'} (${todo.category || '카테고리없음'})</span>
      </div>
      <div>
        <button onclick="editTodo(${todo.id}, '${todo.content.replace(/'/g, "\\'")}', '${todo.category}', ${todo.done})">수정</button>
        <button onclick="deleteTodo(${todo.id})">삭제</button>
      </div>
    `;
    list.appendChild(li);
  });

  updateProgress(doneCount, filtered.length);
}

// 목표 달성도 기능
function updateProgress(done, total) {
  const percent = total ? (done / total) * 100 : 0;
  const progressBar = document.getElementById("progress");
  const progressText = document.getElementById("progress-text");

  progressBar.style.width = percent + "%";
  progressText.textContent = `목표 달성도: ${Math.round(percent)}%`;
}

// 서버에 좋아요 상태 변경 요청
function toggleLike(index, todoId) {
  fetch(`http://52.64.175.1:8080/api/todo/${todoId}/like`, {
    method: "PATCH",
    headers: {
      "Authorization": `Bearer ${token}`
    }
  })
  .then(response => response.json())
  .then(result => {
    if (result.data) {
      likedItems[todoId] = !likedItems[todoId]; // todoId를 기준으로 상태 관리

      const likeButton = document.querySelectorAll(".like-btn")[index];
      const likeCount = document.getElementById(`like-count-${todoId}`);

      // 좋아요 상태에 따라 UI 업데이트
      if (likedItems[todoId]) {
        likeButton.classList.add("liked");
        likeButton.textContent = "♥"; // 좋아요 상태로 변경
      } else {
        likeButton.classList.remove("liked");
        likeButton.textContent = "좋아요"; // 좋아요 취소 상태로 변경
      }

      // 좋아요 개수 업데이트
      likeCount.textContent = result.data ? result.data.likeCount : 0;
    }
  })
  .catch(error => {
    console.error("좋아요 상태 토글 오류: ", error);
    alert("좋아요 상태 변경 중 오류가 발생했습니다.");
  });
}

// 다른 사용자의 최근 할 일(가테고리도 같이이) 조회
function fetchOtherUsersRecentTodos(category){
  let url;

  if (category === "전체") {
    url = "http://52.64.175.1:8080/api/todo/recent/other"
  } else {
    url = `http://52.64.175.1:8080/api/todo/category/other?category=${category}`
  }

  fetch(url, {
    method: "GET",
    headers: {
      "Authorization": `Bearer ${token}`
    }
  })
  .then(response => {
    if (!response.ok) {
      throw new Error('다른 사용자 할일 조회중 오류 발생.');
    }
    return response.json();
  })
  .then(result => {
    console.log("다른 사용자 할일 목록:",result);
    if (!Array.isArray(result.data)){
      console.error("다른 사용자 할일 조회중 오류 발생!", result);
      return
    }
    renderOtherUsersTodos(result.data);
  })
  .catch(error => {
    console.error("다른 사용자 할일 조회중 오류 발생!", error);
    alert("다른 사용자 할일 조회중 오류 발생!");
  });
}

//도전하기 기능
function copyTodo(content, category) {
  const token = localStorage.getItem("accessToken")

  fetch("http://52.64.175.1:8080/api/todo", {
    method:"POST",
    headers: {
      "Content-Type": "application/json",
      "Authorization": `Bearer ${token}`
    },
    body: JSON.stringify({content, category})
  })
  .then(response => {
    if(!response.ok) throw new Error("추가 실패!");
    return response.json();
  })
  .then(() => {
    alert("내 할 일 목록에 추가완료!");
    fetchTodos();
  })
  .catch(err => {
    console.error("도전하기 중 오류 발생: ",err)
    alert("도전하기 중 오류 발생: ");
  });
}

// 다른 사용자의 할일을 좋아요 상태 반영하여 렌더링하는 함수
function renderOtherUsersTodos(data) {
  const otherTodoList = document.getElementById("otherTodoList");
  otherTodoList.innerHTML = ""; // 기존 목록 초기화

  data.forEach((todo, index) => {
    const li = document.createElement("li");
    const isLiked = likedItems[todo.id] || false; // todoId를 기준으로 좋아요 상태를 확인
    
    li.innerHTML = `
      <div class="todo-left">
        <span>${todo.content || '내용없음'} (${todo.category || '카테고리없음'})</span>
      </div>
      <div>
        <button class="like-btn ${isLiked ? 'liked' : ''}" onclick="toggleLike(${index}, ${todo.id})">${isLiked ? '❤️' : '🤍'}</button>
        <span id="like-count-${todo.id}">${todo.likeCount || 0}</span>
         <button class="add-btn" onclick="copyTodo('${todo.content}', '${todo.category}')">도전하기</button>
      </div>
    `;
    otherTodoList.appendChild(li);
  });
}
