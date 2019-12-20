var getFormTime = (timeStamp) => {
    var unixTimestamp = new Date(timeStamp * 1000);
    return unixTimestamp.toLocaleString();
}


module.exports = getFormTime;