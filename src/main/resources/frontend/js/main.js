function onload_ckhswa() {
    hcaptcha.execute({ async: true })
        .then(({ response, key }) => {
            console.log(response, key);
        })
        .catch(err => {
            console.error(err);
        });
}