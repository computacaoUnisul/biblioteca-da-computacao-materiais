# Processamento Digital de Imagens ([Download](https://github.com/tiagoboeing/processamento-digital-de-imagens/releases))
<i>Dá pra arrumar as fotos e tentar deixar bonitinhas com essas funções.</i>
 - Curso: Ciência da Computação 
 - Semestre: 2018/2 
 - Professor: Clávison Zapelini
 - Universidade: UNISUL
 - Campus: Tubarão/SC

**Licença de uso**
Este repositório está totalmente licenciado open-source para a comunidade de desenvolvedores e acadêmicos. Fique livre para clonar, modificar, reproduzir e fazer o que bem entender com este acervo. Fica dispensada a citação de créditos/fonte por qualquer pessoa e em qualquer parte do mundo, ficando isento de quaisquer legislações de licenciamento de software. Todo e qualquer conteúdo tem caráter educacional.
<hr>

**Você poderá aplicar as seguintes técnicas para manipulação de imagens:**

 - Conversão para tons de cinza (preto e branco) utilizando:
	 - Média aritmética
	 - Média ponderada
 - Aplicar limiarização da imagem
 - Conversão para modo negativo
 - Redução de ruído baseada em três técnicas: 
	 - Vizinhança em cruz
	 - Vizinhança em x
	 - Vizinhança 3x3.
 - Aplicar adição ou subtração utilizando duas imagens
 - Obter cor RGB de determinada posição da imagem (clique esquerdo do mouse para capturar posição do cursor e cor a ser obtida)
 - Aplicar moldura em uma das duas imagens

### Problemas conhecidos
Esta aplicação serve apenas para fins educacionais e prática do algoritmo por trás destes processamentos. O tratamento de erros não foi aplicado.

- Alguns métodos não possuem o devido tratamento de erros, resultando no fechamento/travamento do programa ou logs no console;
- Os métodos de redução de ruído utilizam vários *for* resultando em um tempo de processamento elevado no caso de imagens com grandes dimensões; É possível utilizar constantes e orientação a objetos para contornar o problema;
- Ao aplicar moldura ou demais processamentos nos ImageView ocorre de a posição selecionada não ser a mesma do resultado. Para solucionar este problema basta dimensionar a imagem de origem e resultado para as mesmas medidas;
- Salvamento da imagem de resultado pode não funcionar com alguns métodos;

<hr>
Java Fx e telas com <a href="https://gluonhq.com/products/scene-builder/"><b>Scene Builder - Gluon</b></a>
<hr>

![Recursos](https://i.snag.gy/ZQ56Lu.jpg)

# Recursos
<p>Você poderá abrir no máximo duas imagens</p>
<p>É possível salvar o resultado em uma pasta do sistema</p>

## Tons de cinza - média aritmética

![Aritmética](https://i.snag.gy/oczTjb.jpg)

<p>Soma-se os valores atuais de cada pixel da imagem dos três canais (R, G e B) e divide-se pela quantidade de elementos (3).</p>
<p>Média aritmética: R + G + B / 3</p>
<p>Após obter-se a média, ela será aplicada pixel a pixel na imagem</p>

<pre>
[...]
Color previousColor = pr.getColor(i, j);
  double media = ((previousColor.getRed() + 
          previousColor.getGreen() + 
          previousColor.getBlue()) 
          / 3);
          
Color newColor = new Color(media, media, media, previousColor.getOpacity());
pw.setColor(i, j, newColor);
[...]
</pre>

## Tons de cinza - média ponderada

![Ponderada](https://i.snag.gy/4RlbT9.jpg)

<p>A única diferença da média aritmética é que no caso da ponderada, cada canal será multiplicado pelo seu peso. (O peso será informado pelo usuário no formato de porcentagem)</p>
<b>A soma dos pesos não poderá ultrapassar 100% e recomenda-se que ela feche sempre 100%, caso seja inferior o sistema informará com uma janela, porém o funcionamento não será prejudicado.</b>

<pre>
[...]
Color previousColor = pr.getColor(i, j);
  media = (previousColor.getRed() * pcR
          + previousColor.getGreen() * pcG
          + previousColor.getBlue() * pcB)
          /100; //Media Ponderada do RGD
          
Color newColor = new Color(media, media, media, previousColor.getOpacity());
pw.setColor(i, j, newColor);
[...]
</pre>

## Negativo

![Negativo](https://i.snag.gy/Ab51jg.jpg)

<p>Para aplicar o efeito negativo é necessário realizar a conversão de uma imagem para tons de cinza</p>
<pre>
// Controller
@FXML
public void negativa() {
  imgResultado = PDIClass.tonsDeCinza(img1, 0, 0, 0);
  imgResultado = PDIClass.negativa(img1);
  atualizaImageResultado();
}
</pre>

<pre>
// Utils
Color corAnterior = pr.getColor(i, j); //Consegue pegar a cor de um determinado pixel
  Color corNova;

  corNova = new Color(
        ( 1- corAnterior.getRed()), 
        (1 - corAnterior.getGreen()),
        (1 - corAnterior.getBlue()),
        corAnterior.getOpacity()
      );

  pw.setColor(i, j, corNova);
</pre>

## Limiarização
<p>Através de um controle deslizante (SLIDER) o usuário informa a porcentagem de limiar desejada. (0 ~ 1)</p>

![Limiarização](https://i.snag.gy/WX4DPA.jpg)

<p>No caso da limiarização, compara-se a porcentagem informada pelo usuário com o valor do canal RED (R) no pixel a ser analisado. </p>
- Caso o valor do pixel RED seja maior ou igual ( >= ) ao limiar informado, então o pixel em questão passará a ter 100% (1) em todos os canais e a opacidade de sua cor anterior.
- Caso contrário, a nova cor do pixel em questão será preta e terá a opacidade de sua cor anterior.
<b>Podemos tratar as cores da seguinte forma para melhor entendimento:</b>
<ul>
  <li>PRETO = 1 | 0 (RGB)</li>
  <li>BRANCO = 0 | 255 (RGB)</li>
</ul>

<pre>
[...]
Color corAnterior = pr.getColor(i, j); //Consegue pegar a cor de um determinado pixel
  Color corNova;

  if(corAnterior.getRed() >= limiar) {
    corNova = new Color(1, 1, 1, corAnterior.getOpacity());
  }else {
    corNova = new Color(0, 0, 0, corAnterior.getOpacity());
  }
  pw.setColor(i, j, corNova);
[...]
</pre>

## Redução de ruído
Você poderá remover ruído utilizando três técnicas:

![Técnicas de redução de ruído](https://snag.gy/ZRpIlu.jpg)

 - **Vizinhança em cruz:** vizinhos da vertical e horizontal a partir do pixel atual central
 - **Vizinhança em X:** vizinhos das duas diagonais do pixel atual
 - **Vizinhança 3x3:** todos os vizinhos do pixel atual

<p>A lógica utilizada baseia-se em vários `for`</p>
<p>Ao utilizar uma imagem com grandes dimensões o computador executará um processo longo, fazendo com que travamentos ocorram e o tempo de processamento seja cada vez maior.</p>
<p>É recomendado otimizar o código utilizando array multidimensional ou outras técnicas.</p>

![3x3](https://i.snag.gy/l1sDPo.jpg)

**Como funciona?**
O método `ReducaoRuido.reducao3x3(img1, largura, altura)` retorna um ArrayList com a mediana de cada canal da imagem (RGB). Para reduzir ruído devemos aplicar a mediana dos vizinhos do pixel atual visitado a ele.

### Na prática (pseudocódigo)

**Descobrimos as medidas da imagem**

    int width = (int)image.getWidth();
	int height = (int)image.getHeight();

**Percorremos cada pixel da imagem**

    // largura X
	for(int contX = 0; contX < width; contX++) {
				
	// altura Y
	for(int contY = 0; contY < height; contY++) {

**Descobrimos os vizinhos do pixel que estamos visitando e adicionamos ao ArrayList os valores de seu RGB, para calcularmos a mediana posteriormente**

    if(contX == posicaoX && contY == posicaoY) {											
		// percorre todos os vizinhos
		for(int z = 0; z < 9; z++) {
			
			if(z == 0) {
				Color corVizinho = pr.getColor(contX-1, contY+1);
				vizinhosR.add(corVizinho.getRed());
				vizinhosG.add(corVizinho.getGreen());
				vizinhosB.add(corVizinho.getBlue());
			}
			
			[...]
Ordenamos a lista anteriormente e chamamos o método **`mediana(ArrayList<>)`**

**Calcula a mediana de uma lista informada**

    public static Double mediana(ArrayList<Double> lista) {		

		int restoDivisao = lista.size() % 2;
		
		// tem número ao centro
        if(restoDivisao > 0) {
            return lista.get(Math.round(lista.size() / 2));
        } else {
        	// caso não exista número ao centro
            int menor = (lista.size() /2) -1;
            int maior = (lista.size() /2);

            return (lista.get(menor) + lista.get(maior)) /2;
        }
	}
**Adicionamos a mediana de cada canal em um ArrayList que será retornado**

    medianaCanais.add(mediana(vizinhosR));
	medianaCanais.add(mediana(vizinhosG));
	medianaCanais.add(mediana(vizinhosB));

Nesta etapa calculamos as medianas de todos os canais de todos os vizinhos da forma de redução de ruído escolhida. Retornamos um ArrayList contendo as mesmas.

 - **ArrayList[0]** - mediana do canal ( R )
- **ArrayList[1]** - mediana do canal ( G )
-  **ArrayList[2]** - mediana do canal ( B )

Obtemos as medianas de cada canal do pixel atual e aplicamos a ele.

    Color corNova = new Color(medianas.get(0),
				medianas.get(1),
				medianas.get(2),
				1);
	pw.setColor(largura, altura, corNova);

**Como você percebeu, não aplicamos o valor da mediana a todos os vizinhos do pixel em questão, mas sim apenas ao pixel. A técnica escolhida influencia apenas em quais e quantos vizinhos utilizaremos para calcular a mediana.**

## Adição/Subtração

![Adição](https://i.snag.gy/LVHgAp.jpg)

*No caso da adição, a soma das porcentagens não poderá ultrapassar 100%.*

## Moldura em imagem
É possível aplicar uma "moldura" na imagem (apenas para testes). Normalmente este efeito é utilizado em minimapas em softwares de edição, como Photoshop, Illustrator, CorelDraw e outros.

![Moldura](https://i.snag.gy/cniGpd.jpg)

**Ative a opção de uso de moldura no painel lateral,** segure o mouse sobre a imagem desejada, arraste até a posição final e solte o clique.

### Como funciona?

Este método capta o ato de segurar o clique do mouse. Armazena a posição inicial do clique na imagem nas variáveis: `x1 e y1`. 

Para funcionar nas duas imagens criamos uma variável auxiliar que receberá como string mesmo o nome do imageView que estamos manipulando. Mantive o padrão gerado pelo javafx ('imageView1', 'imageView2')

	@FXML
	public void mousePressed(MouseEvent evt){
		ImageView iw = (ImageView)evt.getTarget();
		
		// descobre em qual imagem estamos trabalhando		
		if(evt.getTarget().equals(imageView1)) { evtTarget = "imageView1"; }
		if(evt.getTarget().equals(imageView2)) { evtTarget = "imageView2"; }
		

		if (molduraAtiva.isSelected() == true) {
			if (iw.getImage() != null ) {
				x1 = (int)evt.getX();
				y1 = (int)evt.getY();
			}
		}
	}

Já neste segundo método, obtemos a posição final, quando o mouse foi solto. Armazenamos então nas variáveis `x2 e y2`

    @FXML
    public void mouseReleased(MouseEvent evt){
    	ImageView iw = (ImageView)evt.getTarget();
    
    	if (molduraAtiva.isSelected() == true) {
    		if (iw.getImage() != null) {
    			x2 = (int)evt.getX();
    			y2 = (int)evt.getY();
    			moldura();
    		}
    	}
    }

Agora que sabemos as duas posições de onde aplicar a borda (início/fim), começa o processo chato... Vamos agora receber a imagem,  varrer largura e altura em um *for* e armazenar em um *PixelWriter* para manipular.

    public static Image moldura(Image image, int x1, int x2, int y1, int y2) { 
    try { 
	    [...]
    } catch();
    [..]

Dentro do `try{} catch();` - recebemos a imagem e as posições iniciais e finais da moldura.

    [...]
    int w1 = (int)image.getWidth();
	int h1 = (int)image.getHeight();
	
	PixelReader pr1 = image.getPixelReader();
	
	WritableImage wi = new WritableImage(w1,h1);
	PixelWriter pw = wi.getPixelWriter();
	
    // varre imagem e guarda em um Pixel Writter
    for (int i = 0; i < w1; i++) {
    	for (int j = 0; j < h1; j++) {
    		Color prevColor1 = pr1.getColor(i, j);
    		pw.setColor(i, j, prevColor1);
    	}
    }

Agora temos que fazer um *for* para cada eixo (x1, x2, y1, y2). Só assim será possível aplicar a borda.

    // repetir também para x2, y1 e y2
    // só alterar as variáveis
    for (int k = x1; k < x2; k++) {
    	Color prevColor1 = pr1.getColor(k, y1);
    	if (k <= x2) {
    		double color1 = (25/255);
    		double color2 = (1);
    		double color3 = (40/255);
    		Color newColor = new Color(color1, color2, color3, prevColor1.getOpacity());
    		pw.setColor(k, y1, newColor);
    	}
    }
Ao aplicar o *for* para todos os eixos, vamos retornar uma *WritableImage* e exibir na tela. A chamada final do método ficaria simples:

    // lembre que antes criamos uma forma de saber qual imagem estava sendo manipulada
    imgResultado = Moldura.moldura(img1, x1, x2, y1, y2);
    imageViewResultado.setImage(imgResultado);
    imageViewResultado.setFitWidth(imgResultado.getWidth());

[Nesta linha](https://github.com/tiagoboeing/processamento-digital-de-imagens/blob/362d9bf18e043f5c5284bea1ddb9c03bdbb73058/src/application/SampleController.java#L379) dá pra ver como foi feito para que ambas as imagens aceitem o efeito de moldura.

## [Imagem em 4 frames](https://github.com/tiagoboeing/processamento-digital-de-imagens/commit/76210b62284e513208b0301bca38bdaf147ee9fd)

Esta é uma técnica para separar a imagem em 4 quadros diferentes, conforme imagem abaixo:

![Quadrantes](https://snag.gy/VjiOcx.jpg)


