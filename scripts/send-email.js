const nodemailer = require('nodemailer');

const smtpUsername = process.env.SMTP_USERNAME;
const smtpPassword = process.env.SMTP_PASSWORD;
const prNumber     = process.env.NUMBER_PR;
const tag_name     = process.env.TAG_NAME;

const transporter = nodemailer.createTransport({
  host: 'smtp.gmail.com',
  port: 465,
  secure: true,
  auth: {
    user: smtpUsername,
    pass: smtpPassword,
  },
});

const mailOptions = {
  from: 'joaowudarski2@gmail.com',
  to: 'joaowudarski@gmail.com',
  subject: 'Aprovação da release ' + tag_name + ' para PRODUÇÃO',
  text: 'Para que a nova release vá para o ambiente produtivo, é necessário uma aprovação manual no PR e deve ser dado o merge.\n\n Link do PR: https://github.com/Filmaro/trabalho-edsII/pull/' + prNumber,
};

transporter.sendMail(mailOptions, (error, info) => {
  if (error) {
    console.error('Erro ao enviar o e-mail:', error);
  } else {
    console.log('E-mail enviado com sucesso:', info.response);
  }
});