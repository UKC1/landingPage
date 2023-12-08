(function () {
    const doc = document;
    const rootEl = doc.documentElement;
    const body = doc.body;
    const lightSwitch = doc.querySelector("#lights-toggle");

    const modal = body.querySelector("#modal");
    const btn = body.querySelector("#showModal");
    const span = body.querySelector(".close");
    const checkbox = body.querySelector("#agree"); // 체크박스 선택
    const joinButton = body.querySelector("#submitBtn"); // 참여하기 버튼 선택

    const phoneNumber = body.querySelector("#stuPhoneNumber");
    const age = body.querySelector("#stuAge");

    /* global ScrollReveal */
    const sr = (window.sr = ScrollReveal());

    rootEl.classList.remove("no-js");
    rootEl.classList.add("js");

    window.addEventListener("load", function () {
        body.classList.add("is-loaded");
    });

    // Reveal animations
    function revealAnimations() {
        sr.reveal(".feature", {
            duration: 600,
            distance: "20px",
            easing: "cubic-bezier(0.215, 0.61, 0.355, 1)",
            origin: "right",
            viewFactor: 0.2,
        });
    }

    if (body.classList.contains("has-animations")) {
        window.addEventListener("load", revealAnimations);
    }

    // Light switcher
    if (lightSwitch) {
        window.addEventListener("load", checkLights);
        lightSwitch.addEventListener("change", checkLights);
    }

    function checkLights() {
        let labelText = lightSwitch.parentNode.querySelector(".label-text");
        if (lightSwitch.checked) {
            body.classList.remove("lights-off");
            if (labelText) {
                labelText.innerHTML = " dark";
            }
        } else {
            body.classList.add("lights-off");
            if (labelText) {
                labelText.innerHTML = " light";
            }
        }
    }

    // 초기 상태에서 버튼 비활성화
    joinButton.disabled = true;

    // 모달 외부 터치 이벤트 추가
    window.addEventListener("touchend", function (event) {
        if (event.target == modal) {
            modal.classList.remove("show"); // 클래스 제거
            setTimeout(() => {
                modal.style.display = "none";
                checkbox.disabled = false; // 체크박스 활성화
            }, 500); // 딜레이 추가
        }
    });

    // 체크박스 변경 이벤트 리스너
    checkbox.addEventListener("change", function () {
        joinButton.disabled = !this.checked;
    });

    btn.onclick = function () {
        modal.style.display = "block";
        modal.classList.add("show"); // 클래스 추가
        body.style.overflow = "hidden"; // 스크롤바 제거
        checkbox.disabled = true; // 체크박스 비활성화
    };

    span.onclick = function () {
        modal.classList.remove("show"); // 클래스 제거
        body.style.overflow = "auto"; // 스크롤바 활성화
        setTimeout(() => {
            modal.style.display = "none";
            checkbox.disabled = false; // 체크박스 활성화
        }, 500); // 딜레이 추가
    };

    window.onclick = function (event) {
        if (event.target == modal) {
            modal.classList.remove("show"); // 클래스 제거
            body.style.overflow = "auto"; // 스크롤바 활성화
            setTimeout(() => {
                modal.style.display = "none";
                checkbox.disabled = false; // 체크박스 활성화
            }, 500); // 딜레이 추가
        }
    };

    // 유효성검사 전화번호
    phoneNumber.addEventListener("input", function () {
        var input = this.value.replace(/[^0-9]/g, ""); // 숫자만 추출
        var formatted = "";
        var prefix = "010";

        // '010'으로 시작하는지 확인
        if (input.startsWith(prefix)) {
            if (input.length <= 3) {
                formatted = input; // '010'만 입력된 경우
            } else if (input.length <= 7) {
                formatted = input.substring(0, 3) + "-" + input.substring(3, 7); // 첫 번째 구분자(-) 추가
            } else {
                formatted =
                    input.substring(0, 3) +
                    "-" +
                    input.substring(3, 7) +
                    "-" +
                    input.substring(7, 11); // 두 번째 구분자(-) 추가
            }
        } else {
            if (input.length <= 3) {
                formatted = input; // 입력값이 없을 때는 빈 문자열로 설정
            } else if (input.length <= 4) {
                formatted = prefix + "-" + input; // 첫 번째 구분자(-) 추가
            } else {
                formatted =
                    prefix + "-" + input.substring(0, 4) + "-" + input.substring(4, 8); // 두 번째 구분자(-) 추가
            }
        }
        this.value = formatted;
    });

    // 유효성검사 나이
    age.addEventListener("input", function () {
        const input = this.value.replace(/[^0-9]/g, ""); // 숫자만 추출
        const regex = /^[0-9]{1,2}$/; // 0부터 99까지의 숫자를 허용하는 정규 표현식
        if (!regex.test(input) && input.length != 0) {
            this.value = ""; // 잘못된 입력을 지움
        } else {
            this.value = input; // 올바른 입력을 유지
        }
    });

    document.getElementById("submitBtn").addEventListener("click", function() {
        var data = {
            communicationTimestamp: new Date().toISOString(),
            resultCode: "resultCode-OK",
            resultDescription: "resultCode-OK",
            data: {
                name: document.getElementById("stuName").value,
                age: document.getElementById("stuAge").value,
                phoneNumber: document.getElementById("stuPhoneNumber").value,
                status: "보류",
                memo: "확인 필요"
            }
        };

        console.log(data);

        fetch('/api/student', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify(data)
        })
            // .then(response => response.json())
            .then(response => {
                if (!response.ok) {
                    // 서버로부터 오류 응답을 받은 경우, 에러 메시지를 추출합니다.
                    return response.json().then(err => {throw err;});
                }
                return response.json();
            })
            .then(data => {
                console.log('Success:', data);
                alert("데이터가 성공적으로 전송되었습니다.");
            })
            .catch((error) => {
                console.error('Error:', error);
                // 에러 상황에서 사용자에게 알립니다.
                alert("데이터 전송에 실패했습니다: " + error.resultDescription);
            });
    });

    // document.getElementById('submitBtn').addEventListener('click', function() {
    //     document.getElementById('stuName').value = ''; // 이름 입력란 초기화
    //     document.getElementById('stuAge').value = ''; // 나이 입력란 초기화
    //     document.getElementById('stuPhoneNumber').value = ''; // 전화번호 입력란 초기화
    // });
})();