const SearchResultItem = ({ title, description }) =>  {
    return (
        <div className="search-result-item">
            <h2>{title}</h2>
            <p>{description}</p>
        </div>
    )
}

export default SearchResultItem;