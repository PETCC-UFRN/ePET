package br.ufrn.ePET.error;

public class ValidationErrorDetail extends ErrorDetails{
	private String campo;
	
	private String campoMenssagem;

	public String getCampo() {
		return campo;
	}

	public void setCampo(String campo) {
		this.campo = campo;
	}

	public String getCampoMenssagem() {
		return campoMenssagem;
	}

	public void setCampoMenssagem(String campoMenssagem) {
		this.campoMenssagem = campoMenssagem;
	}

	public static final class Builder{
    	private String titulo;
    	
        private int status;
        
        private String detalhes;
        
        private long timestamp;
        
        private String mensagem;

    	private String campo;
    	
    	private String campoMenssagem;
        
        private Builder() { }
        
        public static Builder newBuilder() {
			return new Builder();
		}

		public Builder setTitulo(String titulo) {
			this.titulo = titulo;
			return this;
		}

		public Builder setStatus(int status) {
			this.status = status;
			return this;
		}

		public Builder setDetalhes(String detalhes) {
			this.detalhes = detalhes;
			return this;
		}

		public Builder setTimestamp(long timestamp) {
			this.timestamp = timestamp;
			return this;
		}

		public Builder setMensagem(String mensagem) {
			this.mensagem = mensagem;
			return this;
		}

		public Builder setCampo(String campo) {
			this.campo = campo;
			return this;
		}
		
		public Builder setCampoMenssagem(String campoMenssagem) {
			this.campoMenssagem = campoMenssagem;
			return this;
		}

        public ValidationErrorDetail build(){
        	ValidationErrorDetail validationerrordetail = new ValidationErrorDetail();
        	validationerrordetail.setTitulo(titulo);
        	validationerrordetail.setStatus(status);
        	validationerrordetail.setDetalhes(detalhes);
        	validationerrordetail.setTimestamp(timestamp);
        	validationerrordetail.setMensagem(mensagem);
        	validationerrordetail.setCampo(campo);
        	validationerrordetail.setCampoMenssagem(campoMenssagem);
        	return validationerrordetail;
        }
    	
    }
	
}
