const nodemailer = require('nodemailer');

const transporter = nodemailer.createTransport({
  host: 'smtp.gmail.com',
  port: 465,
  secure: false,
  auth: {
    user: secrets.EMAIL_USERNAME,
    pass: secrets.EMAIL_PASSWORD,
  },
});

const mailOptions = {
  from: 'joaowudarski2@gmail.com',
  to: 'joaowudarski@gmail.com',
  subject: 'Aprovação da release ' + steps.release.outputs.tag_name + ' para PRODUÇÃO',
  text: 'Para que a nova release vá para o ambiente produtivo, é necessário uma aprovação manual no PR e deve ser dado o merge.\n\n Link do PR: https://github.com/Filmaro/trabalho-edsII/pull/' + steps.release.outputs.number,
};

transporter.sendMail(mailOptions, (error, info) => {
  if (error) {
    console.error('Erro ao enviar o e-mail:', error);
  } else {
    console.log('E-mail enviado com sucesso:', info.response);
  }
});