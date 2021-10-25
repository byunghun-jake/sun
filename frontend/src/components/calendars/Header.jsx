const Header = () => {
  return (
    <header className="p-4 grid gap-4">
      <button className="mr-auto font-bold">000학생의 캘린더</button>
      <div className="flex gap-4 mx-auto">
        <button>이전 월</button>
        <h4>2021년 10월</h4>
        <button>다음 월</button>
      </div>
    </header>
  )
}

export default Header
